package ru.forester.jeevz.service

import com.pengrad.telegrambot.TelegramBot
import com.pengrad.telegrambot.UpdatesListener
import com.pengrad.telegrambot.model.Message
import com.pengrad.telegrambot.model.Update
import com.pengrad.telegrambot.model.request.InlineKeyboardButton
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup
import com.pengrad.telegrambot.request.GetFile
import com.pengrad.telegrambot.request.SendMessage
import mu.KLogging
import org.springframework.stereotype.Service
import ru.forester.jeevz.client.TelegramClient
import ru.forester.jeevz.domain.NotionPage
import ru.forester.jeevz.utils.chatId
import ru.forester.jeevz.utils.sendMessage
import javax.annotation.PostConstruct


@Service
class TelegramService(
    private val jeevzService: JeevzService,
    private val telegramClient: TelegramClient,
    private val bot: TelegramBot,
    //TODO зарефачить и разбить телеграм сервис на листенер и сендер
    private val notionService: NotionService
) {

    fun sendKeyboard(chatId: Any, notionPages: List<NotionPage>) {
        bot.execute(
            SendMessage(chatId, "Choose your book").replyMarkup(
                InlineKeyboardMarkup(*notionPages.map { arrayOf((InlineKeyboardButton(it.name).callbackData(it.id))) }
                    .toTypedArray())
            )
        )
    }

    @PostConstruct
    fun setUpdateListener() {
        bot.setUpdatesListener {
            it.forEach(::eventReceived)
            UpdatesListener.CONFIRMED_UPDATES_ALL
        }
    }

    private fun eventReceived(update: Update) {
        try {
            when {
                update.message()?.photo()?.isNotEmpty() == true -> processPhoto(update)
                update.callbackQuery()?.data() != null -> processCallback(update)
                else -> sendKeyboard(update.chatId(), notionService.getBooksList())
            }
        } catch (e: Exception) {
            logger.error("Exception while handling tlg msg", e)
            sendErrorMessage(e, update)
        }
    }

    private fun processCallback(update: Update) {
        notionService.pageId = update.callbackQuery().data()
        bot.sendMessage(update.chatId(), "Book selected, now you can send photos!")
    }

    private fun processPhoto(update: Update) {
        getFileFromMessage(update.message())
            .let { jeevzService.createQuoteFromPhoto(it) }
            .also { bot.sendMessage(update.chatId(), "Added new quote:\n$it") }
    }

    private fun sendErrorMessage(e: Exception, update: Update) {
        logger.error("Error while processing message from telegram", e)
        bot.sendMessage(update.chatId(), "Smth went wrong:\n${e.message}")
    }

    private fun getFileFromMessage(message: Message) =
        bot.execute(GetFile(message.photo().last().fileId()))
            .let { bot.getFullFilePath(it.file()) }
            .let { telegramClient.downloadFile(it) }

    companion object : KLogging()
}