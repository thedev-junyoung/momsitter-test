package com.momsitter.application.child.dto

import com.momsitter.domain.user.Gender
import com.momsitter.presentation.child.dto.request.UpdateChildRequest
import java.time.LocalDate

data class UpdateChildCommand(
    val userId: Long,
    val childId: Long,
    val name: String?,
    val birthDate: LocalDate?,
    val gender: Gender?
) {
    companion object {
        fun of(userId: Long,  childId: Long, requestDto: UpdateChildRequest): UpdateChildCommand {
            return UpdateChildCommand(
                userId= userId,
                childId = childId,
                name = requestDto.name,
                birthDate = requestDto.birthDate,
                gender = requestDto.gender
            )
        }

    }
}