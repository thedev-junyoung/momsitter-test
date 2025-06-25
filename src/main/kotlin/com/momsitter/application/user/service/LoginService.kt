package com.momsitter.application.user.service

import com.momsitter.application.user.dto.LoginCommand
import com.momsitter.application.user.dto.LoginResult
import com.momsitter.application.user.validator.LoginValidator
import com.momsitter.infrastructure.jwt.JwtTokenProvider
import org.springframework.stereotype.Service

@Service
class LoginService(
    private val loginValidate: LoginValidator,
    private val jwtTokenProvider: JwtTokenProvider
) {
    fun login(request: LoginCommand): LoginResult {
        val user = loginValidate(request.username, request.password)
        val token = jwtTokenProvider.createToken(user.id, user.username)
        return LoginResult(user.id, user.username, token)
    }
}
