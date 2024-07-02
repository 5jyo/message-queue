package com.example.messagequeue.cluster

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class ClusterManager(
    clusterProperties: ClusterProperties,
    @Value("\${node.id}") private val currentNodeId: String,
) {
    private val nodes: List<Node> = clusterProperties.nodes.map { Node.from(it) }

    class NodeNotFoundException(
        message: String,
    ) : RuntimeException(message)

    fun getMaster(): Node = nodes.find { it.isLeader() } ?: throw NodeNotFoundException("master node not found")

    fun isCurrentNodeMaster(): Boolean = this.getMaster().id == currentNodeId

    fun getFollowers(): List<Node> = nodes.filter { !it.isLeader() }
}
