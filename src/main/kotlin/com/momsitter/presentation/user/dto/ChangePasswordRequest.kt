package com.momsitter.presentation.user.dto

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

@Schema(description = "비밀번호 변경 요청")
data class ChangePasswordRequest(
    @Schema(description = "현재 비밀번호", example = "current123!")
    @field:NotBlank
    val oldPassword: String,

    @Schema(description = "새 비밀번호", example = "new456!")
    @field:NotBlank @field:Size(min = 6)
    val newPassword: String
)