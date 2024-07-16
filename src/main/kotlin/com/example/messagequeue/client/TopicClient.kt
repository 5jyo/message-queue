package com.example.messagequeue.client

import com.example.messagequeue.controllers.ProducerController.TopicCreationForm
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.service.annotation.PostExchange

interface TopicClient {
    // Leader -> Follower 호출
    @PostExchange("/cluster/topic/create")
    fun createTopicInNode(@RequestBody topicCreationForm: TopicCreationForm)

    // Follower -> Leader 호출
    @PostExchange("/topics")
    fun createTopicInCluster(@RequestBody topicCreationForm: TopicCreationForm)
}