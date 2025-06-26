package com.momsitter.application.user.validator

import com.momsitter.domain.user.User
import com.momsitter.domain.user.UserRepository
import org.springframework.stereotype.Component

@Component
class UserValidator (
    private val userRepository: UserRepository
){
    fun validateUserId(userId: Long) : User {
        return userRepository.findById(userId).orElseThrow {
            throw IllegalArgumentException("존재하지 않는 사용자입니다. userId: $userId")
        }
    }
    fun validateUserWithParentAndChildren(userId: Long): User {
        return userRepository.findUserWithParentAndChildren(userId)
            ?: throw IllegalArgumentException("존재하지 않는 사용자입니다. userId: $userId")
    }

    fun validateUserWithSitterProfile(userId: Long): User {
        return userRepository.findUserWithSitterProfile(userId)
            ?: throw IllegalArgumentException("존재하지 않는 사용자입니다. userId: $userId")
    }
}