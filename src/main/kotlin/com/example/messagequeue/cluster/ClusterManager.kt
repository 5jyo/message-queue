package com.example.messagequeue.cluster

import com.example.messagequeue.client.TopicClient
import com.example.messagequeue.controllers.ProducerController
import com.example.messagequeue.core.TopicManager
import com.example.messagequeue.core.TopicRouter
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.client.RestClient
import org.springframework.web.client.support.RestClientAdapter
import org.springframework.web.service.invoker.HttpServiceProxyFactory

// 역할: Node 주소와 Status를 관리
// 상호작용
// TopicDispatcher <- 토픽 생성 요청 시 어느 node에 위치하는지 배분
// MessageDispatcher <- 어떤 노드가 살아있고, 메시지 보낼 수 있는지
// HeartBeatScheduler <- healthcheck를 통한 노드 상태 업데이트
@Service
class ClusterManager(
    clusterProperties: ClusterProperties,
    private val topicManager: TopicManager,
    private val topicClient: TopicClient,
    private val topicRouter: TopicRouter,
    @Value("\${node.id}") private val currentNodeId: String,
) {
    private val nodes: List<Node> = clusterProperties.nodes.map { Node.from(it) }
    private val log = KotlinLogging.logger {}

    class NodeNotFoundException(
        message: String,
    ) : RuntimeException(message)

    fun getMaster(): Node = nodes.find { it.isLeader() } ?: throw NodeNotFoundException("master node not found")

    fun isCurrentNodeMaster(): Boolean = this.getMaster().id == currentNodeId

    fun getAddresses(): List<NodeAddress> = nodes.map { NodeAddress(it.id, it.host, it.port) }

    fun setNodeToHealthy(nodeId: String) {
        val node =
            nodes.find { node ->
                node.id == nodeId
            }
        node?.markAsHealthy()
    }

    fun setNodeToUnhealthy(nodeId: String) {
        val node =
            nodes.find { node ->
                node.id == nodeId
            }
        node?.markAsUnhealthy()
    }

    // TEMP
    fun reportStatus() {
        nodes.forEach {
            // println(it)
        }
    }

    // method to add topic in node
    fun createTopic(topicName: String) {
        // TODO: Global topic manager?
        topicManager.addTopic(topicName)
    }

    // method to add topic into cluster
    fun routingTopic(topicName: String) {
        if (this.isCurrentNodeMaster()) {
            if (topicRouter.doesTopicExist(topicName)) {
                log.info("Topic already exists in cluster")
                throw IllegalArgumentException("Topic already exists in cluster")
            }

            // routing algorithm and forward
            val availableNodes = nodes.filter { it.isAvailable() }
            val node = availableNodes[topicName.hashCode() % availableNodes.size]
            log.info("Routing topic to node: ${node.id}")

            val client = getClientForNode(node)
            client.createTopicInNode(ProducerController.TopicCreationForm(topicName))
            topicRouter.saveTopicToNodeMapping(topicName, node)
        } else {
            // Forward request to leader
            val client = getClientForNode(getMaster())
            log.info("Forwarding topic to leader: ${getMaster().id}")
            client.createTopicInCluster(ProducerController.TopicCreationForm(topicName))
        }
    }

    private fun getClientForNode(node: Node): TopicClient {
        val client =
            RestClient
                .builder()
                .baseUrl("http://127.0.0.1:${node.port}")
                .build()
        val adapter = RestClientAdapter.create(client)
        val factory = HttpServiceProxyFactory.builderFor(adapter).build()

        return factory.createClient(TopicClient::class.java)
    }

    data class NodeAddress(
        val id: String,
        val host: String,
        val port: String,
    )
}
