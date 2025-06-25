package com.momsitter.presentation.care.dto

import com.momsitter.application.care.dto.CreateCareRequestCommand

data class CreateCareRequestRequest(
    val content: String
) {
    fun toCommand(userId: Long): CreateCareRequestCommand =
        CreateCareRequestCommand(parentUserId = userId, content = content)
}