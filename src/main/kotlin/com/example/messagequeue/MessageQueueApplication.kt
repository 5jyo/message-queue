package com.example.messagequeue

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MessageQueueApplication

fun main(args: Array<String>) {
	runApplication<MessageQueueApplication>(*args)
}
