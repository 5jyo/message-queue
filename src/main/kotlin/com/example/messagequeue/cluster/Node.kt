package com.example.messagequeue.cluster

class Node(
    val id: String,
    val host: String,
    val port: String,
    private val role: RoleConfig,
) {
    private var status: Status = Status.UNKNOWN

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

    private fun statusIs(status: Status): Boolean = this.status == status

    fun isAvailable(): Boolean = this.statusIs(Status.HEALTHY)

    fun markAsHealthy() {
        this.status = Status.HEALTHY
    }

    fun markAsUnhealthy() {
        this.status = Status.UNHEALTHY
    }

    enum class RoleConfig {
        LEADER,
        FOLLOWER,
    }

    enum class Status {
        UNKNOWN,
        HEALTHY,
        UNHEALTHY,
    }
}
