package com.momsitter.domain.child

import com.momsitter.domain.user.Gender
import java.time.LocalDate

data class ChildInfo(
    val name: String,
    val birthDate: LocalDate,
    val gender: Gender
) {
    companion object {
        fun of(name: String, birthDate: LocalDate, gender: Gender): ChildInfo {
            return ChildInfo(
                name = name,
                birthDate = birthDate,
                gender = gender
            )
        }

        fun from(it: Child): ChildInfo {
            return ChildInfo(
                name = it.name,
                birthDate = it.birthDate,
                gender = it.gender
            )
        }
    }
}