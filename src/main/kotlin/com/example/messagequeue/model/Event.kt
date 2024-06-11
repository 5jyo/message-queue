package com.example.mq.model

import java.util.*


// TODO: consider convert to ordinary class
data class Event private constructor(
    val id: UUID,
    val topicId: String,
    val body: String,
    val created: Long
) {
    companion object {
        fun create(topicId: String, body: String): Event {
            return Event(
                id = UUID.randomUUID(),
                topicId = topicId,
                body = body,
                created = System.currentTimeMillis()
            )
        }
    }
}
