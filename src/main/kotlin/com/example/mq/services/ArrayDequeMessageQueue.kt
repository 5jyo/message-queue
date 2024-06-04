package com.example.mq.services

import org.springframework.stereotype.Service

@Service
class ArrayDequeMessageQueue: Topic {
    private val queue = ArrayDeque<String>()

    override fun produce(message: String) {
        queue.add(message)
    }

    override fun consume(): String? {
        if (queue.isEmpty()) {
            return null
        }

        return queue.removeFirst()
    }
}