package com.example.messagequeue.core

import com.example.messagequeue.model.Event
import org.springframework.stereotype.Component

@Component
class TopicManager(
    private val offsetManager: OffsetManager,
) : Producible, Consumable {
    private val topicToQueueMap = mutableMapOf<String, MutableList<Event>>()

    override fun produce(event: Event) {
        val topic = topicToQueueMap[event.topicId] ?: throw IllegalArgumentException("Topic not found")
        synchronized(topic) {
            topic.add(event)
        }
    }

    override fun consume(topicId: String, consumerId: String): Event? {
        val topic = topicToQueueMap[topicId] ?: throw IllegalArgumentException("Topic not found")
        synchronized(topic) {
            return topic.removeFirstOrNull()
        }
    }

    fun addTopic(topicId: String) {
        topicToQueueMap[topicId] = mutableListOf()
    }

    fun hasTopic(topicId: String): Boolean {
        return topicToQueueMap.containsKey(topicId)
    }

    // TODO: topic안에 Event가 없는 경우만 허용할지?
    fun removeTopic(topicId: String) {
        topicToQueueMap.remove(topicId)
    }

    fun topicSize(topicId: String): Int {
        return topicToQueueMap[topicId]?.size ?: 0
    }

    fun commit(topicId: String, consumerId: String): Int {
        require(isValidTopicId(topicId)) { "Unregistered consumer $consumerId" }

        return offsetManager.commit(
            topicId = topicId,
            consumerId = consumerId
        )
    }

    private fun isValidTopicId(topicId: String) = topicToQueueMap.containsKey(topicId)
}
