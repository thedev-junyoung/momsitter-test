package com.momsitter.presentation.user.controller

import com.momsitter.application.user.dto.command.LoginCommand
import com.momsitter.application.user.service.LoginService
import com.momsitter.common.CustomApiResponse
import com.momsitter.presentation.user.dto.request.LoginRequest
import com.momsitter.presentation.user.dto.response.LoginResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/auth")
class AuthController(
    private val loginService: LoginService
) : AuthAPI {

    @PostMapping("/login")
    override fun login(@RequestBody request: LoginRequest): ResponseEntity<CustomApiResponse<LoginResponse>> {
        val command = LoginCommand(request.username, request.password)
        val result = loginService.login(command)
        return ResponseEntity.ok(
            CustomApiResponse.success(LoginResponse(result.userId, result.username, result.accessToken),"로그인 성공")
        )
    }
}
