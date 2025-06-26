package com.momsitter.presentation.user.dto.request

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank

data class SitterInfoRequest(
    @field:Min(value = 0, message = "최소 연령은 0 이상이어야 합니다.")
    @Schema(description = "케어 가능 최소 연령", example = "3")
    val minAge: Int,

    @field:Min(value = 0, message = "최대 연령은 0 이상이어야 합니다.")
    @Schema(description = "케어 가능 최대 연령", example = "5")
    val maxAge: Int,

    @field:NotBlank(message = "자기소개는 필수입니다.")
    @Schema(description = "자기소개", example = "아이를 좋아하는 교육학과 학생입니다.")
    val introduction: String
)
