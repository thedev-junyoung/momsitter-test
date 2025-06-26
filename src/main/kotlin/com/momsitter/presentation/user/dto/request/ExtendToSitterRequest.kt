package com.momsitter.presentation.user.dto.request

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.*

@Schema(description = "부모 → 시터 역할 확장 요청")
data class ExtendToSitterRequest(

    @field:Min(value = 0, message = "최소 케어 연령은 0세 이상이어야 합니다.")
    @Schema(
        description = "케어 가능 최소 연령",
        example = "2",
        minimum = "0"
    )
    val minCareAge: Int,

    @field:Min(value = 0, message = "최대 케어 연령은 0세 이상이어야 합니다.")
    @Schema(
        description = "케어 가능 최대 연령",
        example = "6",
        minimum = "0"
    )
    val maxCareAge: Int,

    @field:NotBlank(message = "자기소개는 필수입니다.")
    @Schema(
        description = "자기소개",
        example = "아이를 좋아하는 부모입니다. 시터로도 활동하고 싶어요.",
        maxLength = 1000
    )
    val introduction: String
)
