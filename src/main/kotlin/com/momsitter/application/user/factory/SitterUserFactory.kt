package com.momsitter.application.user.factory

import com.momsitter.application.user.dto.SignUpCommand
import com.momsitter.domain.sitter.SitterProfileInfo
import com.momsitter.domain.user.Role
import com.momsitter.domain.user.User
import org.springframework.stereotype.Component

@Component
class SitterUserFactory : UserFactory {
    override fun supportedRole(): String = "SITTER"

    override fun create(command: SignUpCommand, encodedPassword: String, role: Role): User {
        val sitterInfo = command.sitterInfo
            ?: throw IllegalArgumentException("시터 가입 시 sitterInfo는 필수입니다.")

        return User.signUpAsSitter(
            username = command.username,
            password = encodedPassword,
            name = command.name,
            birthDate = command.birthDate,
            gender = command.gender,
            email = command.email,
            role = role,
            sitterInfo = SitterProfileInfo(
                minCareAge = sitterInfo.minCareAge,
                maxCareAge = sitterInfo.maxCareAge,
                introduction = sitterInfo.introduction
            )
        )
    }
}
