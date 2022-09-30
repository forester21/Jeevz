package ru.forester.jeevz.dto.notion

import com.fasterxml.jackson.annotation.JsonProperty

data class NotionRequestDTO(
    val children: List<Children?>? = null
)

data class Children(
    @field:JsonProperty("object")
    val obj: String = "block",
    val type: String? = null,
    val quote: Quote? = null
)

data class Quote(
    @field:JsonProperty("rich_text")
    val richText: List<RichText?>? = null
)

data class RichText(
    val annotations: Annotations? = null,
    val text: Text? = null,
    val type: String = "text"
)

data class Annotations(
    val italic: Boolean? = null
)

data class Text(
    val content: String? = null
)