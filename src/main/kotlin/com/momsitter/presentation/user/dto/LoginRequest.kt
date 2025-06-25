package com.momsitter.presentation.user.dto

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank

@Schema(description = "로그인 요청")
data class LoginRequest(
    @field:NotBlank
    @Schema(description = "아이디", example = "wonderfulPark0206")
    val username: String,

    @field:NotBlank
    @Schema(description = "비밀번호", example = "parak0206%^")
    val password: String
)
