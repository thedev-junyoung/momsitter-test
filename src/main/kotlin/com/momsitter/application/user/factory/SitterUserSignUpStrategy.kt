package com.momsitter.application.user.factory

import com.momsitter.application.user.dto.command.SignUpCommand
import com.momsitter.domain.sitter.SitterUserCreator
import com.momsitter.domain.user.User
import org.springframework.stereotype.Component

@Component
class SitterUserSignUpStrategy(
    private val sitterUserCreator: SitterUserCreator
) : UserSignUpStrategy {
    override fun supports(role: String) = role == "SITTER"

    override fun create(command: SignUpCommand, encodedPassword: String): User {
        return sitterUserCreator.createSitterUser(
            username = command.username,
            password = encodedPassword,
            name = command.name,
            birthDate = command.birthDate,
            gender = command.gender,
            email = command.email,
            sitterInfo = command.sitterInfo!!
        )
    }
}
