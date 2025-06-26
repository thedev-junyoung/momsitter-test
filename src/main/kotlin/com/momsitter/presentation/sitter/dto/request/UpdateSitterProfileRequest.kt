package com.momsitter.presentation.sitter.dto.request

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Min
import java.io.Serializable


data class UpdateSitterProfileRequest(
    @Schema(description = "최소 케어 연령", example = "3")
    @field:Min(1, message = "최소 케어 연령은 1 이상이어야 합니다.")
    val minCareAge: Int?,

    @Schema(description = "최대 케어 연령", example = "12")
    val maxCareAge: Int?,

    @Schema(description = "자기소개", example = "아이들을 사랑하는 시터입니다.")
    val introduction: String?
) : Serializable
