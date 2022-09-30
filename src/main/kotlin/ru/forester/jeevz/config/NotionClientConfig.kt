package ru.forester.jeevz.config

import feign.RequestInterceptor
import feign.okhttp.OkHttpClient
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean


class NotionClientConfig {

    @Value("\${application.client.notion.apiKey}")
    lateinit var apiKey: String

    @Bean
    fun requestInterceptor(): RequestInterceptor {
        return RequestInterceptor {
            it.header("Authorization", "Bearer $apiKey")
            it.header("Notion-Version", NOTION_VERSION)
        }
    }

    @Bean
    fun client(): OkHttpClient {
        return OkHttpClient()
    }

    companion object {
        const val NOTION_VERSION = "2022-02-22"
    }
}