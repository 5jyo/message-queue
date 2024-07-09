package com.example.messagequeue.core

import com.example.messagequeue.cluster.Node
import mu.KotlinLogging
import org.springframework.stereotype.Component

@Component
class TopicRouterImpl : TopicRouter {
    private val topicToNodeMap = mutableMapOf<String, Node>()
    private val log = KotlinLogging.logger {}

    override fun getNode(topicId: String): Node = topicToNodeMap[topicId] ?: error("No node found for topic $topicId")

    override fun saveTopicToNodeMapping(
        topicId: String,
        node: Node,
    ) {
        topicToNodeMap[topicId] = node
        topicToNodeMap.forEach { (key, value) -> log.info("Key: $key, Value: $value") }
    }

    override fun doesTopicExist(topicId: String): Boolean = topicToNodeMap.containsKey(topicId)
}
