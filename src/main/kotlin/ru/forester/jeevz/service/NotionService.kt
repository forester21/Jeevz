package ru.forester.jeevz.service

import org.springframework.stereotype.Service
import ru.forester.jeevz.client.NotionClient
import ru.forester.jeevz.domain.NotionPage
import ru.forester.jeevz.dto.notion.*

@Service
class NotionService(private val notionClient: NotionClient) {

    @Volatile
    var pageId: String? = null

    fun getBooksList() = notionClient.getBlock(ROOT_PAGE).results
        .filter { it.type == Result.CHILD_PAGE_TYPE }
        .map { it.toNotionPage() }

    fun addQuote(quoteText: String) {
        notionClient.updateBlock(pageId!!, buildRequest(quoteText))
    }

    private fun Result.toNotionPage() = NotionPage(
        id = id,
        name = childPage!!.title!!
    )

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

    companion object {
        const val ROOT_PAGE = "d8696a30270f4ea4ab3ea23e9f5168a6"
    }
}