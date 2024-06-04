package com.example.mq.services

interface Topic {
    fun produce(message: String)
    fun consume(): String?
}