package com.example.messagequeue.cluster

import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class HeartBeatService(
    private val happyCluster: HappyCluster,
) {
    @Scheduled(fixedRate = 500)
    fun sendHeartBeat() {
        if (happyCluster.isCurrentNodeMaster()) {
            happyCluster.getFollowerNodes().forEach {
                // use RestTemplate to send a GET request to the follower node
                val response =
                    RestTemplate().getForEntity("http://${it.host}:${it.port}/cluster/health", String::class.java)

                println(response.statusCode)
            }
        }
    }
}
