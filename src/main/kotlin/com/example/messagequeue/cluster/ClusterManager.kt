package com.example.messagequeue.cluster

import com.example.messagequeue.core.TopicManager
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

// 역할: Node 주소와 Status를 관리
// 상호작용
// TopicDispatcher <- 토픽 생성 요청 시 어느 node에 위치하는지 배분
// MessageDispatcher <- 어떤 노드가 살아있고, 메시지 보낼 수 있는지
// HeartBeatScheduler <- healthcheck를 통한 노드 상태 업데이트
@Service
class ClusterManager(
    clusterProperties: ClusterProperties,
    private val topicManager: TopicManager,
    @Value("\${node.id}") private val currentNodeId: String,
) {
    private val nodes: List<Node> = clusterProperties.nodes.map { Node.from(it) }

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
            println(it)
        }
    }

    fun createTopic(topicName: String) {
        // TODO: Global topic manager?
        topicManager.addTopic(topicName)
    }

    fun routingTopic(topicName: String) {
        if (this.isCurrentNodeMaster()) {
            // routing algorithm and forward
        } else {
            // forward to master
        }
    }

    data class NodeAddress(
        val id: String,
        val host: String,
        val port: String,
    )
}
