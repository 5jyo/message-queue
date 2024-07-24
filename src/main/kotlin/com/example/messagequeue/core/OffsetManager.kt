package com.example.messagequeue.core

import org.springframework.stereotype.Component

@Component
class OffsetManager(
    private val offsetRepository: OffsetRepository,
) {

    fun increment(consumerId: String, topicId: String): Int {
        val offsetKey = Pair(consumerId, topicId)
        return offsetRepository.increment(offsetKey)
    }

    fun getOffset(consumerId: String, topicId: String): Int {
        val offsetKey = Pair(consumerId, topicId)
        try {
            return offsetRepository.get(offsetKey)
        } catch (e: RuntimeException) {
            val offset = offsetRepository.create(offsetKey)
            return offset
        }
    }
}

