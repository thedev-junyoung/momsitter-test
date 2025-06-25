package com.momsitter.presentation.user.dto.request

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.*

@Schema(description = "부모 -> 시터 역할 확장 요청")
data class ExtendToSitterRequest(
    @field:Min(0)
    @Schema(description = "케어 가능 최소 연령", example = "2")
    val minCareAge: Int,

    @field:Min(0)
    @Schema(description = "케어 가능 최대 연령", example = "6")
    val maxCareAge: Int,

    @field:NotBlank
    @Schema(description = "자기소개", example = "아이를 좋아하는 부모입니다. 시터로도 활동하고 싶어요.")
    val introduction: String
)
