package com.momsitter.application.user.factory

import com.momsitter.application.user.dto.command.SignUpCommand
import com.momsitter.domain.user.User
import com.momsitter.domain.user.UserRoleType
import org.springframework.stereotype.Component

@Component
class ParentUserFactory : UserFactory {
    override fun supportedRole(): UserRoleType = UserRoleType.PARENT

    override fun create(command: SignUpCommand, encodedPassword: String, role: UserRoleType): User {
        val parentInfo = command.parentInfo
        return if (parentInfo == null || parentInfo.children.isEmpty()) {
            User.signUpAsParentOnly(
                username = command.username,
                password = encodedPassword,
                name = command.name,
                birthDate = command.birthDate,
                gender = command.gender,
                email = command.email,
                activeRole = role,
                role = role
            )
        } else {
            User.signUpAsParentWithChildren(
                username = command.username,
                password = encodedPassword,
                name = command.name,
                birthDate = command.birthDate,
                gender = command.gender,
                email = command.email,
                role = role,
                activeRole = role,
                children = parentInfo.children
            )
        }
    }
}
