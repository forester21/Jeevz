package ru.forester.jeevz.utils

import com.pengrad.telegrambot.TelegramBot
import com.pengrad.telegrambot.model.Update
import com.pengrad.telegrambot.request.SendMessage

fun Update.chatId(): Long = message()?.chat()?.id() ?: callbackQuery().message().chat().id()

fun TelegramBot.sendMessage(chatId: Any, message: String) = tryToDo { execute(SendMessage(chatId, message)) }