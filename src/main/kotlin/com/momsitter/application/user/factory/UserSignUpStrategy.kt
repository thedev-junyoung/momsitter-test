package com.momsitter.application.user.factory

import com.momsitter.application.user.dto.command.SignUpCommand
import com.momsitter.domain.user.User

interface UserSignUpStrategy {
    fun supports(role: String): Boolean
    fun create(command: SignUpCommand, encodedPassword: String): User
}
