package com.example.mq.controllers

import com.example.mq.services.MessageQueueInterface
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ConsumerController @Autowired constructor(private val messageQueue: MessageQueueInterface) {
    @GetMapping("/consume")
    fun consume(): String {
        return messageQueue.consume()
    }
}