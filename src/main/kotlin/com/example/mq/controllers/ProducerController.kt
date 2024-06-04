package com.example.mq.controllers

import com.example.mq.core.TopicManager
import com.example.mq.model.Event
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class ProducerController(
    private val topicManager: TopicManager,
) {
    @PostMapping("/produce")
    fun produce(@RequestBody requestForm: RequestForm) {
        val event = Event.create(requestForm.topicId, requestForm.message)
        return topicManager.produce(event)
    }

    @PostMapping("/topics")
    fun createTopic(@RequestBody topicCreationForm: TopicCreationForm) {
        return topicManager.addTopic(topicCreationForm.topicId)
    }

    data class TopicCreationForm(
        val topicId: String
    )

    data class RequestForm(
        val topicId: String,
        val message: String
    )
}