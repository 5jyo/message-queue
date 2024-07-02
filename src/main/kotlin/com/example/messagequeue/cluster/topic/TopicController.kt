package com.example.messagequeue.cluster.topic

import com.example.messagequeue.cluster.ClusterManager
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TopicController(
    private val clusterManager: ClusterManager,
) {
    @PostMapping("/cluster/topic/create")
    fun createTopic(topicName: String) {
        clusterManager.createTopic(topicName)
    }
}
