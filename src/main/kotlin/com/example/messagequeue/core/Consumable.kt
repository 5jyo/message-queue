package com.example.messagequeue.core

import com.example.messagequeue.model.Event

fun interface Consumable {
    fun consume(topicId: String, consumerId: String): Event?
}
