package ru.forester.jeevz.service

import net.sourceforge.tess4j.Tesseract
import org.springframework.stereotype.Service
import java.io.File


@Service
class OcrService(private val tesseract: Tesseract) {

    fun recognizeText(image: File): String {
        return tesseract.doOCR(image).removeLineBreaks()
    }

    private fun String.removeLineBreaks() = this
        .replace("-\n", "")
        .replace("\n", " ")
}