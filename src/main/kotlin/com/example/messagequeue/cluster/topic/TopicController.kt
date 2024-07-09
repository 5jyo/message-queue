package com.example.messagequeue.cluster.topic

import com.example.messagequeue.cluster.ClusterManager
import com.example.messagequeue.controllers.ProducerController
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class TopicController(
    private val clusterManager: ClusterManager,
) {
    @PostMapping("/cluster/topic/create")
    fun createTopic(@RequestBody topicCreationForm: ProducerController.TopicCreationForm) {
        clusterManager.createTopic(topicCreationForm.topicId)
    }
}
