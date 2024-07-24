package com.example.messagequeue.cluster.message

import com.example.messagequeue.core.TopicManager
import com.example.messagequeue.model.Event
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class ClusterConsumeController(
    private val topicManager: TopicManager,
) {
    @PostMapping("/cluster/consume")
    fun consume(
        @RequestBody request: ConsumeRequest,
    ): Event =
        topicManager.consume(
            request.topicId,
            request.consumerId,
        ) ?: throw RuntimeException("can't consume")

    @PostMapping("/cluster/commit")
    fun commit(
        @RequestBody request: CommitRequest,
    ): CommitResponse =
        CommitResponse(
            topicId = request.topicId,
            consumerId = request.consumerId,
            next =
            topicManager.commit(
                topicId = request.topicId,
                consumerId = request.consumerId,
            ),
        )

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
