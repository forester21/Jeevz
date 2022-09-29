package ru.forester.jeevz.client

import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service
import org.springframework.util.StreamUtils
import org.springframework.web.client.RestTemplate
import java.io.File
import java.io.FileOutputStream
import java.net.URI


@Service
class TelegramClient(private val restTemplate: RestTemplate) {

    fun downloadFile(fullPath: String): File {
        return restTemplate.execute(URI.create(fullPath), HttpMethod.GET, null) { clientHttpResponse ->
            val ret: File = File.createTempFile("download", ".jpg")
            StreamUtils.copy(clientHttpResponse.body, FileOutputStream(ret))
            ret
        }!!
    }

}