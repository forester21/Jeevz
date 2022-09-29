package ru.forester.jeevz.config

import net.sourceforge.tess4j.Tesseract
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class TesseractConfig {

    @Bean
    fun tesseract() = Tesseract().apply {
        setDatapath("src/main/resources/traineddata")
        setLanguage("rus")
        setPageSegMode(1)
        setOcrEngineMode(1)
    }

}