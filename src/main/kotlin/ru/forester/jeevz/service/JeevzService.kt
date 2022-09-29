package ru.forester.jeevz.service

import org.springframework.stereotype.Service
import java.io.File

@Service
class JeevzService(
    private val ocrService: OcrService,
    private val notionService: NotionService
) {

    fun createQuoteFromPhoto() {
        val image = File("")
        ocrService.recognizeText(image).let {
            notionService.addQuote(it)
        }
    }
}