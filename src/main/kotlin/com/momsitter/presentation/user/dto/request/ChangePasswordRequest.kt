package com.momsitter.presentation.user.dto.request

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

@Schema(description = "비밀번호 변경 요청")
data class ChangePasswordRequest(

    @field:NotBlank(message = "현재 비밀번호는 필수입니다.")
    @Schema(
        description = "현재 비밀번호",
        example = "current123!",
        minLength = 6,
        maxLength = 50
    )
    val oldPassword: String,

    @field:NotBlank(message = "새 비밀번호는 필수입니다.")
    @field:Size(min = 6, max = 50, message = "비밀번호는 최소 6자 이상이어야 합니다.")
    @Schema(
        description = "새 비밀번호 (6자 이상, 영문/숫자/특수문자 조합 권장)",
        example = "new456!",
        minLength = 6,
        maxLength = 50
    )
    val newPassword: String
)
