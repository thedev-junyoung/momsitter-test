package com.momsitter.application.user.factory

import com.momsitter.application.user.dto.command.SignUpCommand
import com.momsitter.domain.parent.ParentUserCreator
import com.momsitter.domain.user.User
import org.springframework.stereotype.Component


@Component
class ParentUserSignUpStrategy(
    private val parentUserCreator: ParentUserCreator
) : UserSignUpStrategy {
    override fun supports(role: String) = role == "PARENT"

    override fun create(command: SignUpCommand, encodedPassword: String): User {

        val parentInfo = command.parentInfo
        requireNotNull(parentInfo) { "parentInfo must not be null for PARENT role" }

        return if (parentInfo.children.isEmpty()) {
            parentUserCreator.createWithoutChildren(
                username = command.username,
                password = encodedPassword,
                name = command.name,
                birthDate = command.birthDate,
                gender = command.gender,
                email = command.email,
            )
        } else {
            parentUserCreator.createWithChildren(
                username = command.username,
                password = encodedPassword,
                name = command.name,
                birthDate = command.birthDate,
                gender = command.gender,
                email = command.email,
                children = parentInfo.children
            )
        }
    }
}