package com.example.messagequeue.controllers

import com.example.messagequeue.cluster.ClusterManager
import com.example.messagequeue.core.TopicManager
import com.example.messagequeue.model.Event
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class ProducerController(
    private val topicManager: TopicManager,
    private val clusterManager: ClusterManager,
) {
    @PostMapping("/produce")
    fun produce(
        @RequestBody requestForm: RequestForm,
    ) {
        val event = Event.create(requestForm.topicId, requestForm.message)
        return topicManager.produce(event)
    }

    @PostMapping("/topics")
    fun createTopic(
        @RequestBody topicCreationForm: TopicCreationForm,
    ) = clusterManager.routingTopic(topicCreationForm.topicId)

    data class TopicCreationForm(
        val topicId: String,
    )

    data class RequestForm(
        val topicId: String,
        val message: String,
    )
}
