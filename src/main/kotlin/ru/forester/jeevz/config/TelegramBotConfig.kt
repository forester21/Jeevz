package ru.forester.jeevz.config

import com.pengrad.telegrambot.TelegramBot
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class TelegramBotConfig {

    @Value("\${application.client.telegram.apiKey}")
    lateinit var apiKey: String

    @Bean
    fun telegramBot() = TelegramBot(apiKey)
}