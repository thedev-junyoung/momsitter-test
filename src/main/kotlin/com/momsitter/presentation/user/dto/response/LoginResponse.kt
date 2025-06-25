package com.momsitter.presentation.user.dto.response

import io.swagger.v3.oas.annotations.media.Schema


@Schema(description = "로그인 응답")
data class LoginResponse(
    @Schema(description = "회원 ID", example = "1")
    val userId: Long,

    @Schema(description = "아이디", example = "wonderfulPark0206")
    val username: String,

    @Schema(description = "JWT 액세스 토큰", example = "eyJhbGciOiJIUzI1NiIsInR...")
    val accessToken: String
)
