package com.example.messagequeue.cluster.client

import com.example.messagequeue.model.Event
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.service.annotation.PostExchange
import org.springframework.web.util.UriBuilderFactory

interface EventClient {
    @PostExchange("/cluster/event/create")
    fun create(
        factory: UriBuilderFactory,
        @RequestBody event: Event,
    )

    @PostExchange("/cluster/event/route")
    fun route(
        factory: UriBuilderFactory,
        @RequestBody event: Event,
    )

    @PostExchange("/consume")
    fun consumeRoute(
        factory: UriBuilderFactory,
        @RequestBody consumeRequest: ConsumeRouteRequest,
    ): Event

    @PostExchange("/cluster/consume")
    fun clusterConsume(
        factory: UriBuilderFactory,
        @RequestBody consumeRequest: ConsumeRouteRequest,
    ): Event

    @PostExchange("/commit")
    fun commitRoute(
        factory: UriBuilderFactory,
        @RequestBody commitRequest: CommitRouteRequest,
    ): CommitRouteResponse

    @PostExchange("/cluster/commit")
    fun clusterCommit(
        factory: UriBuilderFactory,
        @RequestBody commitRequest: CommitRouteRequest,
    ): CommitRouteResponse

    data class ConsumeRouteRequest(
        val topicId: String,
        val consumerId: String,
    )

    data class CommitRouteRequest(
        val topicId: String,
        val consumerId: String,
    )

    data class CommitRouteResponse(
        val topicId: String,
        val consumerId: String,
        val message: String = "OK",
        val next: Int,
    )
}
