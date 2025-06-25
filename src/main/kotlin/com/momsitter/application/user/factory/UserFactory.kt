package com.momsitter.application.user.factory

import com.momsitter.application.user.dto.SignUpCommand
import com.momsitter.domain.user.Role
import com.momsitter.domain.user.User

interface UserFactory {
    fun supportedRole(): String
    fun create(command: SignUpCommand, encodedPassword: String, role: Role): User
}
