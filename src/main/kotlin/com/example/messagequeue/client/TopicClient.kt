package com.example.messagequeue.client

import com.example.messagequeue.controllers.ProducerController.TopicCreationForm
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.service.annotation.PostExchange

interface TopicClient {
    @PostExchange
    fun test(
        @RequestBody topicCreationForm: TopicCreationForm,
    )
}
