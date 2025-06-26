package com.momsitter.presentation.user.dto.request

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank

@Schema(description = "로그인 요청 DTO")
data class LoginRequest(

    @field:NotBlank(message = "아이디는 필수입니다.")
    @Schema(
        description = "사용자 아이디 (username)",
        example = "wonderfulPark0206"
    )
    val username: String,

    @field:NotBlank(message = "비밀번호는 필수입니다.")
    @Schema(
        description = "비밀번호",
        example = "parak0206%^"
    )
    val password: String
)
