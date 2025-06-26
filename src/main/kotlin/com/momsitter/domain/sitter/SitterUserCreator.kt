package com.momsitter.domain.sitter

import com.momsitter.domain.user.*
import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
class SitterUserCreator {
    fun createSitterUser(
        username: String,
        password: String,
        name: String,
        birthDate: LocalDate,
        gender: Gender,
        email: String,
        sitterInfo: SitterProfileInfo
    ): User {
        val user = User.basicUser(
            username = username,
            password = password,
            name = name,
            birthDate = birthDate,
            gender = gender,
            email = email
        )
        val sitterProfile = SitterProfile.of(user, sitterInfo.minCareAge, sitterInfo.maxCareAge, sitterInfo.introduction)
        user.assignSitterProfile(sitterProfile)
        return user
    }
}
