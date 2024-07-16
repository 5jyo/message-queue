package com.example.messagequeue.cluster.event

import com.example.messagequeue.cluster.ClusterManager
import com.example.messagequeue.model.Event
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class EventController(
    private val clusterManager: ClusterManager,
) {
    @PostMapping("/cluster/event/create")
    fun produce(
        @RequestBody event: Event,
    ) {
        clusterManager.createEvent(event)
    }

    @PostMapping("/cluster/event/route")
    fun route(
        @RequestBody event: Event,
    ) {
        clusterManager.routingEvent(event)
    }
}
