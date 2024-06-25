package com.example.messagequeue.core

import com.example.messagequeue.model.Event

// TODO: 시스템 외부의 Producer 와 개념이 겹치지 않나?
fun interface Producible {
    fun produce(event: Event)
}
