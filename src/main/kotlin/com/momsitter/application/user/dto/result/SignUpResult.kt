package com.momsitter.application.user.dto.result

import com.momsitter.domain.user.User

data class SignUpResult(
    val userId: Long,
    val username: String,
    val role: String
) {
    companion object {
        fun from(user: User): SignUpResult {
            return SignUpResult(
                userId = user.id,
                username = user.username,
                role = user.roles.first().name
            )
        }
    }
}
