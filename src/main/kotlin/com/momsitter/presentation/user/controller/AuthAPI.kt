package com.momsitter.presentation.user.controller

import com.momsitter.common.CustomApiResponse
import com.momsitter.presentation.user.dto.LoginRequest
import com.momsitter.presentation.user.dto.LoginResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity

@Tag(name = "Auth", description = "인증 API")
interface AuthAPI {

    @Operation(summary = "로그인", description = "아이디/비밀번호로 로그인합니다.")
    fun login(@Valid request: LoginRequest): ResponseEntity<CustomApiResponse<LoginResponse>>
}
