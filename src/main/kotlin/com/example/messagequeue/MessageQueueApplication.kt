package com.example.messagequeue

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@EnableScheduling
@SpringBootApplication
class MessageQueueApplication

fun main(args: Array<String>) {
    runApplication<MessageQueueApplication>(*args)
}
