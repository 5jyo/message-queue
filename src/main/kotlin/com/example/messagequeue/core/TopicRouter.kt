package com.example.messagequeue.core

import com.example.messagequeue.cluster.Node

interface TopicRouter {
    fun getNode(topicId: String): Node
    fun saveTopicToNodeMapping(topicId: String, node: Node)
    fun doesTopicExist(topicId: String): Boolean
}