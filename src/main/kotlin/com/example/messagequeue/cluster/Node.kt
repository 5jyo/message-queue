package com.example.messagequeue.cluster

class Node(
    val id: String,
    val host: String,
    val port: String,
    private val role: RoleConfig,
) {
    companion object {
        fun from(nodeConfig: ClusterProperties.NodeConfig): Node =
            Node(
                nodeConfig.id,
                nodeConfig.host,
                nodeConfig.port,
                RoleConfig.valueOf(nodeConfig.role),
            )
    }

    fun isLeader(): Boolean = this.role == RoleConfig.LEADER

    enum class RoleConfig {
        LEADER,
        FOLLOWER,
    }
}
