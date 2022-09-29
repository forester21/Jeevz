package ru.forester.jeevz.service

import com.pengrad.telegrambot.TelegramBot
import com.pengrad.telegrambot.UpdatesListener
import com.pengrad.telegrambot.model.Update
import com.pengrad.telegrambot.request.GetFile
import com.pengrad.telegrambot.request.SendMessage
import org.springframework.stereotype.Service
import ru.forester.jeevz.client.TelegramClient
import ru.forester.jeevz.utils.tryToDo
import javax.annotation.PostConstruct


@Service
class TelegramService(
    private val jeevzService: JeevzService,
    private val telegramClient: TelegramClient
) {

    lateinit var bot: TelegramBot

    @PostConstruct
    fun init() {
        bot = TelegramBot("changeMe")
        bot.setUpdatesListener {
            tryToDo { it.forEach(::eventReceived) }
            UpdatesListener.CONFIRMED_UPDATES_ALL
        }
    }

    fun eventReceived(update: Update) {
        try {
            val request = GetFile(update.message().photo().last().fileId())
            val filePath = bot.getFullFilePath(bot.execute(request).file())
            telegramClient.downloadFile(filePath)
                .let { jeevzService.createQuoteFromPhoto(it) }
                .let { bot.execute(SendMessage(update.message().chat().id(), "Added new quote:\n$it")) }
        } catch (e: Exception) {
            //TODO change to logger
            println(e)
            bot.execute(SendMessage(update.message().chat().id(), "Smth went wrong:\n${e.message}"))
        }
    }

}