package com.example.messagequeue.core

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class TopicManagerLockTest : BehaviorSpec({
    given("multiple threads") {
        `when`("producing") {
            then("should not lose any message") {

            }
        }
    }
})
