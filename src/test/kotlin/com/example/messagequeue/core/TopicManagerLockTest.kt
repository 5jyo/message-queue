package com.example.messagequeue.core

import io.kotest.core.spec.style.BehaviorSpec

class TopicManagerLockTest : BehaviorSpec({
    given("multiple threads") {
        `when`("producing") {
            then("should not lose any message") {

            }
        }
    }
})
