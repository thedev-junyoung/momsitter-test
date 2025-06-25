package com.momsitter.application.user.dto.result

import com.momsitter.domain.child.Child
import java.time.format.DateTimeFormatter

data class ChildResult(
    val name: String,
    val birthDate: String,
    val gender: String
) {
    companion object {
        fun from(child: Child): ChildResult {
            return ChildResult(
                name = child.name,
                birthDate = child.birthDate.format(DateTimeFormatter.BASIC_ISO_DATE),
                gender = child.gender.korName
            )
        }
    }
}
