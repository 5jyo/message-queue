package com.example.messagequeue.test

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe

class KotestUseTest : BehaviorSpec({
    Given("given") {
        When("when") {
            Then("then") {
                true shouldBe true

            }
        }
    }
})


