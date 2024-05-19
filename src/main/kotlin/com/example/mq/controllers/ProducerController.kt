package com.example.mq.controllers

import com.example.mq.services.MessageQueueInterface
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class ProducerController @Autowired constructor(private val messageQueue: MessageQueueInterface){
    @PostMapping("/produce")
    fun produce(@RequestBody message: String) {
        return messageQueue.produce(message)
    }
}