package com.example.messagequeue.test

import com.example.messagequeue.core.OffsetManager
import com.example.messagequeue.core.TopicManager
import com.example.messagequeue.model.Event
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import java.util.concurrent.Executors

class KotestUseTest :
    BehaviorSpec({
        context("multi thread setting") {
            Given("100 threads") {
                val manager = TopicManager(OffsetManager())
                val iterationCount = 100_000
                val threadCount = 100
                val experimentCount = 20
                val topicId = "test-topic"
                manager.addTopic(topicId)
                When("producing 100,000 events") {
                    val elapsedTimes = mutableListOf<Long>()
                    repeat(experimentCount) {
                        val startTime = System.currentTimeMillis()
                        val executors = Executors.newFixedThreadPool(threadCount)
                        repeat(iterationCount) {
                            executors.submit {
                                manager.produce(
                                    Event.create(
                                        topicId = topicId,
                                        body = "test",
                                    ),
                                )
                            }
                        }
                        executors.shutdown()
                        executors.awaitTermination(5000, java.util.concurrent.TimeUnit.MILLISECONDS)
                        val elapsedTime = System.currentTimeMillis() - startTime
                        elapsedTimes.add(elapsedTime)
                    }

                    Then("event should not be lost") {
                        val expectedCount = iterationCount * experimentCount
                        manager.topicSize(topicId = topicId) shouldBe expectedCount
                    }
                    println("elapsedTimes: $elapsedTimes ms")
                    println("elapsedTimes: ${elapsedTimes.average()} ms")
                }
            }
            Given("100,000 events") {
                val manager = TopicManager(OffsetManager())
                val topicId = "test-topic"
                val experimentCount = 20
                val iterationCount = 100_000
                val threadCount = 100
                val consumerId = "test-consumer"

                manager.addTopic(topicId)

                When("consuming 100,000 events") {
                    val elapsedTimes = mutableListOf<Long>()
                    repeat(experimentCount) {
                        repeat(iterationCount) {
                            manager.produce(
                                Event.create(
                                    topicId = topicId,
                                    body = "test",
                                ),
                            )
                        }
                        val startTime = System.currentTimeMillis()
                        val executors = Executors.newFixedThreadPool(threadCount)
                        repeat(iterationCount) {
                            executors.submit {
                                manager.consume(topicId, consumerId)
                            }
                        }
                        executors.shutdown()
                        executors.awaitTermination(5000, java.util.concurrent.TimeUnit.MILLISECONDS)
                        val elapsedTime = System.currentTimeMillis() - startTime
                        elapsedTimes.add(elapsedTime)
                    }

                    Then("topic should be empty") {
                        manager.topicSize(topicId = topicId) shouldBe 0
                    }

                    println("elapsedTimes: $elapsedTimes ms")
                    println("elapsedTimes: ${elapsedTimes.average()} ms")
                }
            }
        }
    })
