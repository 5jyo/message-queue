package com.example.mq.services

import org.springframework.stereotype.Service

@Service
class ArrayDequeMessageQueue: MessageQueueInterface {
    private val queue = ArrayDeque<String>()

    override fun produce(message: String) {
        queue.add(message)
    }

    override fun consume(): String {
        return queue.removeFirst()
    }
}