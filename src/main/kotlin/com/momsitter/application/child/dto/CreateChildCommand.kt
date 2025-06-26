package com.momsitter.application.child.dto

import com.momsitter.domain.user.Gender
import com.momsitter.presentation.child.dto.request.CreateChildRequest
import java.time.LocalDate

data class CreateChildCommand(
    val userId: Long,
    val name: String,
    val birthDate: LocalDate,
    val gender: Gender
){
    companion object {
        fun of(userId: Long, requestDto: CreateChildRequest): CreateChildCommand {
            return CreateChildCommand(userId, requestDto.name, requestDto.birthDate, requestDto.gender)
        }
    }

}
