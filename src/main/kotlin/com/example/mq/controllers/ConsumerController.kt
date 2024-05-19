package com.example.mq.controllers

import com.example.mq.services.MessageQueueInterface
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

@RestController
class ConsumerController @Autowired constructor(private val messageQueue: MessageQueueInterface) {
    @GetMapping("/consume")
    fun consume(): String {
        val message = messageQueue.consume()

        if (message == null) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "No message to consume")
        }

        return message
    }
}