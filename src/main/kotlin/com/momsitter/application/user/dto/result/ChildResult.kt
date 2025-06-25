package com.momsitter.application.user.dto.result

import com.momsitter.domain.child.Child
import java.time.LocalDate
import java.time.format.DateTimeFormatter

data class ChildResult(
    val name: String,
    val birthDate: LocalDate,
    val gender: String
) {
    companion object {
        fun from(child: Child): ChildResult {
            return ChildResult(
                name = child.name,
                birthDate = child.birthDate,
                gender = child.gender.korName
            )
        }
    }
}
