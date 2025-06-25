package com.momsitter.presentation.user.controller

import com.momsitter.presentation.user.dto.SignupRequest
import com.momsitter.presentation.user.dto.SignupResponse
import com.momsitter.common.CustomApiResponse
import com.momsitter.presentation.user.dto.MyInfoResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.servlet.http.HttpServletRequest
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity

@Tag(name = "User", description = "유저 회원가입 API")
interface UserAPI {

    @Operation(summary = "회원가입", description = "회원가입을 진행합니다.")
    fun signup(@Valid request: SignupRequest): ResponseEntity<CustomApiResponse<SignupResponse>>

    @Operation(summary = "내 정보 조회", description = "인증된 사용자의 정보를 조회합니다.")
    fun getMyInfo(request: HttpServletRequest): ResponseEntity<CustomApiResponse<MyInfoResponse>>
}
