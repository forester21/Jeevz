package ru.forester.jeevz.client

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import ru.forester.jeevz.config.NotionClientConfig
import ru.forester.jeevz.dto.notion.ChildrenResponseDTO
import ru.forester.jeevz.dto.notion.NotionRequestDTO

@FeignClient(
    name = "NotionClient",
    url = "https://api.notion.com/v1/",
    configuration = [NotionClientConfig::class]
)
interface NotionClient {

    @PatchMapping("/blocks/{blockId}/children")
    fun updateBlock(
        @PathVariable blockId: String,
        @RequestBody notionRequestDTO: NotionRequestDTO
    )

    @GetMapping("/blocks/{blockId}/children")
    fun getBlock(@PathVariable blockId: String): ChildrenResponseDTO
}