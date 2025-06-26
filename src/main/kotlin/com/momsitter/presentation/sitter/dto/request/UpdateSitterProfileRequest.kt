package com.momsitter.presentation.sitter.dto.request

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.Size
import java.io.Serializable

data class UpdateSitterProfileRequest(

    @Schema(
        description = "최소 케어 연령 (1세 이상)",
        example = "3",
        nullable = true,
        required = false
    )
    @field:Min(1, message = "최소 케어 연령은 1 이상이어야 합니다.")
    val minCareAge: Int? = null,

    @Schema(
        description = "최대 케어 연령",
        example = "12",
        nullable = true,
        required = false
    )
    val maxCareAge: Int? = null,

    @Schema(
        description = "시터 자기소개",
        example = "아이들을 사랑하는 시터입니다. 안전하고 따뜻하게 돌보겠습니다.",
        nullable = true,
        required = false
    )
    @field:Size(max = 1000, message = "자기소개는 최대 1000자까지 입력 가능합니다.")
    val introduction: String? = null

) : Serializable
