package com.example.messagequeue.core

import org.springframework.stereotype.Component
import java.util.concurrent.atomic.AtomicInteger

@Component
class OffsetManager {
    private val offsets = mutableMapOf<Pair<String, String>, AtomicInteger>()

    fun increment(consumerId: String, topicId: String): Int {
        val offsetKey = Pair(consumerId, topicId)
        val offset = offsets[offsetKey] ?: throw RuntimeException("Invalid key, $topicId, $consumerId")

        return offset.incrementAndGet()
    }

    fun getOffset(consumerId: String, topicId: String): Int {
        val offsetKey = Pair(consumerId, topicId)
        return offsets.getOrPut(offsetKey) { AtomicInteger(0) }.get()
    }
}

