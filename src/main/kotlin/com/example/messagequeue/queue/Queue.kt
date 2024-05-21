package com.example.messagequeue.queue

class Queue {
    private val queue = mutableListOf<String>()

    fun enqueue(message: String) {
        queue.add(message)
    }

    fun dequeue(): String? {
        if (queue.isEmpty()) {
            return null
        }
        return queue.removeAt(0)
    }

    fun peek(): String? {
        if (queue.isEmpty()) {
            return null
        }
        return queue[0]
    }

    fun size(): Int {
        return queue.size
    }

    fun isEmpty(): Boolean {
        return queue.isEmpty()
    }
}