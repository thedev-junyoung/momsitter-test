package com.momsitter.presentation.user.dto.request

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Email

@Schema(description = "유저 정보 수정 요청")
data class UpdateUserInfoRequest(
    @Schema(description = "수정할 이름", example = "김시터")
    val name: String? = null,

    @Schema(description = "수정할 이메일", example = "newemail@example.com")
    @field:Email
    val email: String? = null,

    @Schema(description = "수정할 생년월일", example = "2000-01-01", type = "string", format = "date")
    val birthDate: String? = null,

    @Schema(description = "수정할 성별", example = "FEMALE", allowableValues = ["MALE", "FEMALE"])
    val gender: String? = null
)
