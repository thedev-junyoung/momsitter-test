package com.momsitter.application.user.dto.command

import com.momsitter.domain.child.ChildInfo
import com.momsitter.domain.parent.ParentProfileInfo
import com.momsitter.domain.sitter.SitterProfileInfo
import com.momsitter.domain.user.Gender
import com.momsitter.presentation.user.dto.SignupRequest
import java.time.LocalDate

data class SignUpCommand(
    val username: String,
    val rawPassword: String,
    val name: String,
    val birthDate: LocalDate,
    val gender: Gender,
    val email: String,
    val roles: String,

    val sitterInfo: SitterProfileInfo?,     // 시터로 가입할 경우만 입력
    val parentInfo: ParentProfileInfo?      // 부모로 가입할 경우만 입력
) {
    companion object {
        fun of(request: SignupRequest): SignUpCommand {
            return SignUpCommand(
                username = request.username,
                rawPassword = request.password,
                name = request.name,
                birthDate = request.birthDate,
                gender = Gender.valueOf(request.gender.uppercase()),
                email = request.email,
                roles = request.roles,
                sitterInfo = request.sitter?.let {
                    SitterProfileInfo.of(it.minAge, it.maxAge, it.introduction)
                },
                parentInfo = request.parent?.let {
                    ParentProfileInfo.of(
                        it.children.map { child ->
                            ChildInfo.of(child.name, child.birthDate, Gender.valueOf(child.gender.uppercase()))
                        }
                    )
                }
            )
        }
    }
}