package com.example.mq.core

import com.example.mq.model.Event

fun interface Consumable {
    fun consume(topicId: String): Event?
}
