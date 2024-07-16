package com.example.messagequeue.controllers

import com.example.messagequeue.cluster.ClusterManager
import com.example.messagequeue.core.TopicManager
import com.example.messagequeue.model.Event
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class ConsumerController(
    private val topicManager: TopicManager,
    private val clusterManager: ClusterManager,
) {
    @PostMapping("/consume")
    fun consume(
        @RequestBody request: ConsumeRequest,
    ): Event =
        clusterManager.consumeEvent(
            topicId = request.topicId,
            consumerId = request.consumerId,
        )

    @PostMapping("/commit")
    fun commit(
        @RequestBody request: CommitRequest,
    ): CommitResponse {
        val next =
            clusterManager.commitEvent(
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
