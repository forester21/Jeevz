package ru.forester.jeevz.service

import net.sourceforge.tess4j.Tesseract
import org.springframework.stereotype.Service
import ru.forester.jeevz.utils.runIf
import java.io.File


@Service
class OcrService(private val tesseract: Tesseract) {

    fun recognizeText(image: File): String {
        return tesseract.doOCR(image)
            .removeLineBreaks()
            .trimIncompleteSentences()
    }

    private fun String.removeLineBreaks() = this
        .replace("-\n", "")
        .replace("\n", " ")

    private fun String.trimIncompleteSentences() = this
        .trim()
        .runIf(!first().isUpperCase()) {
            dropWhile { !it.isEndOfSentence() }.drop(1)
        }
        .dropLastWhile { !it.isEndOfSentence() }
        .trim()

    private fun Char.isEndOfSentence() = SENTENCE_END_SIGNS.contains(this)

    companion object {
        val SENTENCE_END_SIGNS = setOf('.', '!', '?')
    }
}