package com.example.mq

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MqApplication

fun main(args: Array<String>) {
	runApplication<MqApplication>(*args)
}
