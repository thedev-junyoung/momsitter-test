package com.momsitter.presentation.user.dto

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.Valid

@Schema(description = "시터 → 부모 역할 확장 요청")
data class ExtendToParentRequest(
    @field:Valid
    @Schema(description = "자녀 정보 목록", required = false)
    val children: List<ChildRequest>? = null
)

