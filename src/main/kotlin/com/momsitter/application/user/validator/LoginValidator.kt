package com.momsitter.application.user.validator

import com.momsitter.common.BusinessException
import com.momsitter.common.ErrorCode
import com.momsitter.domain.PasswordEncoder
import com.momsitter.domain.user.User
import com.momsitter.domain.user.UserRepository
import org.springframework.stereotype.Component

@Component
class LoginValidator (
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
){

    operator fun invoke(username: String, password: String): User {
        val user = userRepository.findByUsername(username)
            ?: throw BusinessException("존재하지 않는 사용자입니다.", ErrorCode.USER_NOT_FOUND)

        if (!passwordEncoder.matches(password, user.password)) {
            throw BusinessException("비밀번호가 일치하지 않습니다.", ErrorCode.INVALID_PASSWORD)
        }
        return user
    }
}