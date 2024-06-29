package com.example.messagequeue.cluster.health

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/cluster")
class HealthCheckController {
    @GetMapping("/health")
    fun health(): String = "OK"
}
