package com.example.messagequeue.cluster.message

import com.example.messagequeue.cluster.ClusterManager
import com.example.messagequeue.model.Event
import org.springframework.stereotype.Component
import org.springframework.web.client.RestClient

@Component
class FollowerRoutingService(
    private val clusterManager: ClusterManager
) : RoutingService {
    override fun routeConsume(topicId: String, consumerId: String): Event {
        return internalCall(clusterManager.getMaster().host, "/cluster/consume", topicId, consumerId)
    }

    override fun routeCommit(topicId: String, consumerId: String): Int {
        return internalCall(clusterManager.getMaster().host, "/cluster/commit", topicId, consumerId)
    }

    private fun <T> internalCall(host: String, path: String, topicId: String, consumerId: String): T {
        return RestClient.create()
            .post()
            .uri(host + path)
            .retrieve() as T
    }
}