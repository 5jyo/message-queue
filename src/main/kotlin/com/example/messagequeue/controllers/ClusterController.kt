package com.example.messagequeue.controllers

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/cluster")
class ClusterController {
    @GetMapping("/health")
    fun giveHeartBeat(): String = "OK"
}
