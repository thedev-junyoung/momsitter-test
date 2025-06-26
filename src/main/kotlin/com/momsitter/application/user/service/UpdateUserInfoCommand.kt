package com.momsitter.application.user.service

import com.momsitter.domain.user.Gender
import java.time.LocalDate

data class UpdateUserInfoCommand(
    val userId: Long,
    val name: String? = null,
    val email: String? = null,
    val birthDate: LocalDate? = null,
    val gender: Gender? = null,

) {
    companion object {
        fun of(
            userId: Long,
            name: String? = null,
            email: String? = null,
            birthDate: String? = null,
            gender: String? = null,
        ): UpdateUserInfoCommand {
            val parsedBirthDate = birthDate?.let {
                try {
                    LocalDate.parse(it)
                } catch (e: Exception) {
                    throw IllegalArgumentException("잘못된 생년월일 형식입니다. (예: 2000-01-01)")
                }
            }

            val parsedGender = gender?.let {
                try {
                    Gender.valueOf(it.uppercase())
                } catch (e: Exception) {
                    throw IllegalArgumentException("지원하지 않는 성별입니다. (MALE 또는 FEMALE)")
                }
            }

            return UpdateUserInfoCommand(
                userId = userId,
                name = name,
                email = email,
                birthDate = parsedBirthDate,
                gender = parsedGender
            )
        }
    }
}
