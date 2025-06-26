package com.momsitter.presentation.user.dto.request

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.Valid

data class ParentInfoRequest(
    @field:Valid
    @Schema(description = "아이 정보 목록")
    val children: List<ChildRequest>
)
