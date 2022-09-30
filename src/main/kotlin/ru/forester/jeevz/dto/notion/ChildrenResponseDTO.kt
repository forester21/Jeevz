package ru.forester.jeevz.dto.notion

import com.fasterxml.jackson.annotation.JsonProperty

data class ChildrenResponseDTO(
    val results: List<Result>
)

data class Result(
    val id: String,
    @field:JsonProperty("child_page")
    val childPage: ChildPage? = null,
    val type: String? = null
) {
    companion object {
        const val CHILD_PAGE_TYPE = "child_page"
    }
}

data class ChildPage(
    val title: String? = null
)