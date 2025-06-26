package com.momsitter.presentation.user.dto.request

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank

@Schema(description = "활성 역할 변경 요청")
data class ChangeRoleRequest(

    @field:NotBlank(message = "변경할 역할은 필수입니다.")
    @Schema(
        description = "변경할 역할 (SITTER 또는 PARENT 중 하나)",
        example = "SITTER",
        allowableValues = ["SITTER", "PARENT"]
    )
    val newRole: String
)
