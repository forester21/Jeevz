package ru.forester.jeevz.service

import org.springframework.stereotype.Service
import ru.forester.jeevz.client.NotionClient
import ru.forester.jeevz.dto.notion.*

@Service
class NotionService(private val notionClient: NotionClient) {

    fun addQuote(quoteText: String) {
        val blockId = "885bf64a3ad44ddaa2c9f4834a611feb"
        notionClient.updateBlock(blockId, buildRequest(quoteText))
    }

    private fun buildRequest(quoteText: String) = NotionRequestDTO(
        children = listOf(
            Children(
                type = "quote",
                quote = Quote(
                    listOf(
                        RichText(
                            annotations = Annotations(italic = true),
                            text = Text(quoteText)
                        )
                    )
                )
            )
        )
    )
}