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
}
