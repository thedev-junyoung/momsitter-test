package com.momsitter.presentation.user.controller

import com.momsitter.common.CustomApiResponse
import com.momsitter.presentation.user.dto.*
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

    @Operation(summary = "부모 → 시터 역할 확장", description = "부모 역할의 사용자가 시터 역할을 추가합니다.")
    fun extendToSitter(
        request: HttpServletRequest,
        @Valid requestDto: ExtendToSitterRequest
    ): ResponseEntity<CustomApiResponse<ExtendToSitterResponse>>

    @Operation(summary = "시터 → 부모 역할 확장", description = "시터 역할의 사용자가 부모 역할을 추가합니다.")
    fun extendToParent(
        request: HttpServletRequest,
        @Valid requestDto: ExtendToParentRequest
    ): ResponseEntity<CustomApiResponse<ExtendToParentResponse>>

}
