package com.example.messagequeue.infrastructure

import io.mockk.impl.annotations.InjectMockKs
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class MemoryOffsetRepositoryTest {
    @InjectMockKs
    private lateinit var memoryOffsetRepository: MemoryOffsetRepository

    @Test
    fun `increment should increase offset`() {
        val consumerId = "consumer1"
        val topicId = "topic1"
        val initialOffset = 0
        memoryOffsetRepository.create(Pair(consumerId, topicId))
        val incrementedOffset = memoryOffsetRepository.increment(Pair(consumerId, topicId))

        assertEquals(initialOffset + 1, incrementedOffset)
    }

    @Test
    fun `get should return zero for new key`() {
        val consumerId = "newConsumer"
        val topicId = "newTopic"
        memoryOffsetRepository.create(Pair(consumerId, topicId))

        val offset = memoryOffsetRepository.get(Pair(consumerId, topicId))

        assertEquals(0, offset)
    }

    @Test
    fun `get should return current offset for existing key`() {
        val consumerId = "consumer1"
        val topicId = "topic1"
        val initialOffset = 0
        memoryOffsetRepository.create(Pair(consumerId, topicId))
        memoryOffsetRepository.increment(Pair(consumerId, topicId))

        val offset = memoryOffsetRepository.get(Pair(consumerId, topicId))

        assertEquals(initialOffset + 1, offset)
    }

    @Test
    fun `increment should throw exception for invalid key`() {
        val invalidKey = Pair("invalidConsumer", "invalidTopic")

        assertThrows<RuntimeException> {
            memoryOffsetRepository.increment(invalidKey)
        }
    }

    @Test
    fun `get should throw exception for invalid key`() {
        val invalidKey = Pair("invalidConsumer", "invalidTopic")

        assertThrows<RuntimeException> {
            memoryOffsetRepository.get(invalidKey)
        }
    }

}
