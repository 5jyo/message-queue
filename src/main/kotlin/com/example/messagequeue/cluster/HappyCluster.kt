package com.example.messagequeue.cluster

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class HappyCluster(
    private val clusterProperties: ClusterProperties,
    @Value("\${node.id}") val id: String,
) {
    fun isCurrentNodeMaster(): Boolean = clusterProperties.nodes.find { it.id == id }?.role == RoleConfig.LEADER

    fun getCurrentNodeRole(): RoleConfig = clusterProperties.nodes.find { it.id == id }!!.role

    fun getMasterNode(): NodeConfig = clusterProperties.nodes.find { it.role == RoleConfig.LEADER }!!

    fun getFollowerNodes(): List<NodeConfig> = clusterProperties.nodes.filter { it.role == RoleConfig.FOLLOWER }

    fun getTopology(): List<NodeConfig> = clusterProperties.nodes
}
