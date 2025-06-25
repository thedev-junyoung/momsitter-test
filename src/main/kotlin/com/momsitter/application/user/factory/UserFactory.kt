package com.momsitter.application.user.factory

import com.momsitter.application.user.dto.SignUpCommand
import com.momsitter.domain.user.User
import com.momsitter.domain.user.UserRoleType

interface UserFactory {
    fun supportedRole(): UserRoleType
    fun create(command: SignUpCommand, encodedPassword: String, role: UserRoleType): User
}
