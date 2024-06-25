package com.example.messagequeue.controllers

import com.example.messagequeue.core.TopicManager
import com.example.messagequeue.model.Event
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

@RestController
class ConsumerController(
    private val topicManager: TopicManager,
) {
    @PostMapping("/consume")
    fun consume(@RequestBody requestForm: RequestForm): Event {
        return topicManager.consume(requestForm.topicId) ?: throw ResponseStatusException(
            HttpStatus.NOT_FOUND,
            "No message to consume"
        )
    }

    @PostMapping("/commit")
    fun commit(
        @RequestBody request: CommitRequest,
    ): CommitResponse {
        topicManager.commit(
            topicId = request.topicId,
            consumerId = request.consumerId,
        )

        return CommitResponse(
            topicId = request.topicId,
            consumerId = request.consumerId,
        )
    }

    data class CommitRequest(
        val topicId: String,
        val consumerId: String,
    )

    data class CommitResponse(
        val topicId: String,
        val consumerId: String,
        val message: String = "OK",
    )

    data class RequestForm(
        val topicId: String,
    )
}
