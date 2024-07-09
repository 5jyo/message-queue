package com.example.messagequeue.core

interface OffsetRepository {
    fun get(offsetKey: Pair<String, String>): Int
    fun increment(offsetKey: Pair<String, String>): Int
    fun create(offsetKey: Pair<String, String>): Int
}