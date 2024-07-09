package com.example.messagequeue.infrastructure

import com.example.messagequeue.core.OffsetRepository
import org.springframework.stereotype.Repository
import java.util.concurrent.atomic.AtomicInteger

@Repository
class MemoryOffsetRepository : OffsetRepository {
    private val offsets = mutableMapOf<Pair<String, String>, AtomicInteger>()

    override fun get(offsetKey: Pair<String, String>): Int {
        val offset = offsets[offsetKey] ?: throw RuntimeException("Invalid key, $offsetKey")
        return offset.get()
    }

    override fun increment(offsetKey: Pair<String, String>): Int {
        return offsets[offsetKey]?.incrementAndGet() ?: throw RuntimeException("Invalid key, $offsetKey")
    }

    override fun create(offsetKey: Pair<String, String>): Int {
        offsets[offsetKey] = AtomicInteger(0)
        return offsets[offsetKey]!!.get()
    }
}