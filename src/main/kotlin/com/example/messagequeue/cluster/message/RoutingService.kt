package com.example.messagequeue.cluster.message

import com.example.messagequeue.model.Event

interface RoutingService {
    fun routeConsume(topicId: String, consumerId: String): Event
    fun routeCommit(topicId: String, consumerId: String): Int
}