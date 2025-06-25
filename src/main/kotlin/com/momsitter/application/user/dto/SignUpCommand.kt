package com.momsitter.application.user.dto

import com.momsitter.domain.parent.ParentProfileInfo
import com.momsitter.domain.sitter.SitterProfileInfo
import com.momsitter.domain.user.Gender
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
)