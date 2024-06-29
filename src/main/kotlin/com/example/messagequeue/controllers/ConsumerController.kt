package com.example.messagequeue.controllers

import com.example.messagequeue.core.Consumable
import com.example.messagequeue.model.Event
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

@RestController
class ConsumerController(
    private val messageQueue: Consumable,
) {
    @PostMapping("/consume")
    fun consume(
        @RequestBody requestForm: RequestForm,
    ): Event =
        messageQueue.consume(requestForm.topicId) ?: throw ResponseStatusException(
            HttpStatus.NOT_FOUND,
            "No message to consume",
        )

    data class RequestForm(
        val topicId: String,
    )
}
