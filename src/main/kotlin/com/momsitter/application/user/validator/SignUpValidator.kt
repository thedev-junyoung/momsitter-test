package com.momsitter.application.user.validator

import com.momsitter.domain.user.UserRepository
import org.springframework.stereotype.Component

@Component
class SignUpValidator(
    private val userRepository: UserRepository,
) {

    fun validate(userName: String, userEmail: String) {
        // 1. 아이디 중복 검사
        if (userRepository.existsByUsername(userName)) {
            throw IllegalArgumentException("이미 사용 중인 아이디입니다.")
        }

        // 2. 이메일 중복 검사
        if (userRepository.existsByEmail(userEmail)) {
            throw IllegalArgumentException("이미 사용 중인 이메일입니다.")
        }

    }
}