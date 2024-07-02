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
    fun consume(
        @RequestBody request: ConsumeRequest,
    ): Event {
        return topicManager.consume(
            topicId = request.topicId,
            consumerId = request.consumerId
        ) ?: throw ResponseStatusException(
            HttpStatus.NOT_FOUND,
            "No message to consume"
        )
    }

    @PostMapping("/commit")
    fun commit(
        @RequestBody request: CommitRequest,
    ): CommitResponse {
        val next = topicManager.commit(
            topicId = request.topicId,
            consumerId = request.consumerId,
        )

        return CommitResponse(
            topicId = request.topicId,
            consumerId = request.consumerId,
            next = next,
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
        val next: Int,
    )

    data class ConsumeRequest(
        val topicId: String,
        val consumerId: String = "", // TODO: Implement internal commit logic
    )
}
