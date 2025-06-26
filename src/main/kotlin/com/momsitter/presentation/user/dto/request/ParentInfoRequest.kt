package com.momsitter.presentation.user.dto.request

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.Valid
import jakarta.validation.constraints.NotEmpty

data class ParentInfoRequest(
    @field:NotEmpty(message = "자녀 정보는 1명 이상 필수입니다.")
    @field:Valid
    @Schema(description = "아이 정보 목록")
    val children: List<ChildRequest>
)
