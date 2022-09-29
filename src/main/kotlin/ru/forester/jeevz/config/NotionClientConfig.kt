package ru.forester.jeevz.config

import feign.RequestInterceptor
import feign.okhttp.OkHttpClient
import org.springframework.context.annotation.Bean


class NotionClientConfig {

    @Bean
    fun requestInterceptor(): RequestInterceptor {
        return RequestInterceptor {
            it.header("Authorization", "Bearer $API_TOKEN")
            it.header("Notion-Version", NOTION_VERSION)
        }
    }

    @Bean
    fun client(): OkHttpClient {
        return OkHttpClient()
    }

    companion object {
        const val API_TOKEN = "replaceMe"
        const val NOTION_VERSION = "2022-02-22"
    }
}