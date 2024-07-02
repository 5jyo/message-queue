package com.example.messagequeue.cluster

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "cluster")
class ClusterProperties {
    var nodes: List<NodeConfig> = emptyList()

    data class NodeConfig(
        val host: String,
        val port: String,
        val id: String,
        val role: String,
    )
}
