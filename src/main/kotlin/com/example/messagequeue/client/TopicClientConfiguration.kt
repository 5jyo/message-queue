package com.example.messagequeue.client

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestClient
import org.springframework.web.client.support.RestClientAdapter
import org.springframework.web.service.invoker.HttpServiceProxyFactory

@Configuration
class TopicClientConfiguration {
    @Bean
    fun topicClient(): TopicClient {
        val client =
            RestClient
                .builder()
                .baseUrl("http://127.0.0.1:8080")
                .build()
        val adapter = RestClientAdapter.create(client)
        val factory = HttpServiceProxyFactory.builderFor(adapter).build()

        return factory.createClient(TopicClient::class.java)
    }
}
