package com.example.messagequeue.queue

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class QueueTest : BehaviorSpec({
    var queue = Queue()

    afterTest {
        queue = Queue()
    }

    given("a new queue") {
        `when`("dequeue is called when empty") {
            val message = queue.dequeue()
            then("message should be null") {
                message shouldBe null
            }
        }
        `when`("enqueue is called") {
            queue.enqueue("message")
            then("size should be 1") {
                queue.size() shouldBe 1
                queue.peek() shouldBe "message"
            }
        }
        `when`("dequeue is called") {
            queue.enqueue("message")
            val message = queue.dequeue()
            then("message should be message") {
                message shouldBe "message"
            }
        }
        `when`("isEmpty is called") {
            val empty = queue.isEmpty()
            then("empty should be true") {
                empty shouldBe true
            }
        }
    }
})
