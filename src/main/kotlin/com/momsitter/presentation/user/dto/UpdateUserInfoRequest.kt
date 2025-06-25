package com.momsitter.presentation.user.dto

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Email

@Schema(description = "유저 정보 수정 요청")
data class UpdateUserInfoRequest(
    @Schema(description = "수정할 이름", example = "김시터")
    val name: String? = null,

    @Schema(description = "수정할 이메일", example = "newemail@example.com")
    @field:Email
    val email: String? = null
)