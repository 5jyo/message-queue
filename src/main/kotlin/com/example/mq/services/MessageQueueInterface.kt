package com.example.mq.services

interface MessageQueueInterface {
    fun produce(message: String)
    fun consume(): String
}