package ru.forester.jeevz.service

import org.springframework.stereotype.Service
import java.io.File

@Service
class JeevzService(
    private val ocrService: OcrService,
    private val notionService: NotionService
) {

    fun createQuoteFromPhoto(image: File): String {
        return ocrService.recognizeText(image).also {
            notionService.addQuote(it)
        }
    }
}