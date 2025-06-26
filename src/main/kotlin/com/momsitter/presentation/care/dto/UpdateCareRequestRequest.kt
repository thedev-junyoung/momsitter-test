package com.momsitter.presentation.care.dto

import com.momsitter.application.care.dto.UpdateCareRequestCommand
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank


data class UpdateCareRequestRequest(
    @field:NotBlank
    @Schema(description = "수정된 돌봄 요청 내용", example = "돌봄 시간이 변경되었습니다. 오후 2시부터 부탁드립니다.", required = true)
    val content: String
) {
    fun toCommand(requestId: Long, userId: Long): UpdateCareRequestCommand =
        UpdateCareRequestCommand(requestId = requestId, parentUserId = userId, content = content)
}
