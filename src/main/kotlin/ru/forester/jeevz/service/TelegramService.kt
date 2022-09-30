package ru.forester.jeevz.service

import com.pengrad.telegrambot.TelegramBot
import com.pengrad.telegrambot.UpdatesListener
import com.pengrad.telegrambot.model.Message
import com.pengrad.telegrambot.model.Update
import com.pengrad.telegrambot.request.GetFile
import mu.KLogging
import org.springframework.stereotype.Service
import ru.forester.jeevz.client.TelegramClient
import ru.forester.jeevz.utils.chatId
import ru.forester.jeevz.utils.sendMessage
import javax.annotation.PostConstruct


@Service
class TelegramService(
    private val jeevzService: JeevzService,
    private val telegramClient: TelegramClient,
    private val telegramBot: TelegramBot
) {

    @PostConstruct
    fun setUpdateListener() {
        telegramBot.setUpdatesListener {
            it.forEach(::eventReceived)
            UpdatesListener.CONFIRMED_UPDATES_ALL
        }
    }

    fun eventReceived(update: Update) {
        try {
            getFileFromMessage(update.message())
                .let { jeevzService.createQuoteFromPhoto(it) }
                .also { telegramBot.sendMessage(update.chatId(), "Added new quote:\n$it") }
        } catch (e: Exception) {
            sendErrorMessage(e, update)
        }
    }

    private fun sendErrorMessage(e: Exception, update: Update){
        logger.error("Error while processing message from telegram", e)
        telegramBot.sendMessage(update.chatId(), "Smth went wrong:\n${e.message}")
    }
    private fun getFileFromMessage(message: Message) =
        telegramBot.execute(GetFile(message.photo().last().fileId()))
            .let { telegramBot.getFullFilePath(it.file()) }
            .let { telegramClient.downloadFile(it) }

    companion object : KLogging()
}