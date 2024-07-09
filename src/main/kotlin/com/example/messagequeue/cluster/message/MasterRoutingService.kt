package com.example.messagequeue.cluster.message

import com.example.messagequeue.cluster.ClusterManager
import com.example.messagequeue.cluster.Node
import com.example.messagequeue.core.TopicRouter
import com.example.messagequeue.model.Event
import org.springframework.stereotype.Component
import org.springframework.web.client.RestClient

@Component
class MasterRoutingService (
    private val topicRouter: TopicRouter
) : RoutingService {

    override fun routeConsume(topicId: String, consumerId: String): Event {
        val node = topicRouter.getNode(topicId)
        return internalCall(node.host, "/consume", topicId, consumerId)
    }

    override fun routeCommit(topicId: String, consumerId: String): Int {
        val node = topicRouter.getNode(topicId)
        return internalCall(node.host, "/commit", topicId, consumerId)
        // if current master
        //   find topic node
        //   call private API for commit to follower
        // else
        //   proxy request to master
    }

    private fun <T> internalCall(host: String, path: String, topicId: String, consumerId: String): T {
        return RestClient.create()
            .post()
            .uri(host + path)
            .retrieve() as T
    }

}