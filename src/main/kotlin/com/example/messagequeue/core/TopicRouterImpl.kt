package com.example.messagequeue.core

import com.example.messagequeue.cluster.Node
import org.springframework.stereotype.Component

@Component
class TopicRouterImpl : TopicRouter {
    private val topicToNodeMap = mutableMapOf<String, Node>()

    override fun getNode(topicId: String): Node {
        return topicToNodeMap[topicId] ?: error("No node found for topic $topicId")
    }

    override fun saveTopicToNodeMapping(topicId: String, node: Node) {
        topicToNodeMap[topicId] = node
        topicToNodeMap.forEach { (key, value) -> println("Key: $key, Value: $value") }
    }
}