package com.momsitter.presentation.care.dto

import com.momsitter.application.care.dto.CreateCareRequestCommand
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank

data class CreateCareRequestRequest(

    @field:NotBlank
    @Schema(description = "돌봄 요청 내용", example = "3세 아이 돌봄이 필요합니다.", required = true)
    val content: String
) {
    fun toCommand(userId: Long): CreateCareRequestCommand =
        CreateCareRequestCommand(parentUserId = userId, content = content)
}