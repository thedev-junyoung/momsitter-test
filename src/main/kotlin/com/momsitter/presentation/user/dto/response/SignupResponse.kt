package com.momsitter.presentation.user.dto.response

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "회원가입 응답 DTO")
data class SignupResponse(
    @Schema(description = "사용자 ID", example = "1")
    val userId: Long,

    @Schema(description = "아이디", example = "wonderfulPark0206")
    val username: String,

    @Schema(description = "역할", example = "SITTER")
    val role: String
)
