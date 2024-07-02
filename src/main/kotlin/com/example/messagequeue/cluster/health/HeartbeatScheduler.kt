package com.example.messagequeue.cluster.health

import com.example.messagequeue.cluster.ClusterManager
import com.example.messagequeue.cluster.Node
import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

@Component
class HeartbeatScheduler(
    private val clusterManager: ClusterManager,
) {
    val log = KotlinLogging.logger {}

    class HeartbeatFailureException : RuntimeException()

    @Scheduled(fixedRate = 500)
    fun checkHeartbeatOfFollowers() {
        if (!clusterManager.isCurrentNodeMaster()) {
            return
        }

        clusterManager.getFollowers().forEach { node: Node ->
            checkHealth(node).onFailure {
                log.error { "heartbeat failed for ${node.id} child" }
            }
        }
    }

    private fun checkHealth(it: Node): Result<Unit> =
        runCatching {
            RestTemplate()
                .getForEntity("http://${it.host}:${it.port}/cluster/health", String::class.java)
                .let {
                    if (it.statusCode != HttpStatus.OK) {
                        throw HeartbeatFailureException()
                    }
                }
        }
}
