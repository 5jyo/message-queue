package com.example.messagequeue.controller

import com.example.messagequeue.queue.Queue
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class MessageQueueController(private val queue: Queue) {
    //write controller for queue

    @PostMapping("/enqueue")
    fun postMessage(@RequestBody requestForm: RequestForm): ResponseEntity<String> {
        queue.enqueue(requestForm.message)
        return ResponseEntity.ok(null)
    }

    @GetMapping("/dequeue")
    fun getMessage(): ResponseEntity<ResponseBody> {
        return ResponseEntity.ok(ResponseBody(queue.dequeue()))
    }

    @GetMapping("/monitor/queue")
    fun getQueueSize(): ResponseEntity<Int> {
        return ResponseEntity.ok(queue.size())
    }
}

data class ResponseBody (
    val message: String?
)

data class RequestForm (
    val message: String
)