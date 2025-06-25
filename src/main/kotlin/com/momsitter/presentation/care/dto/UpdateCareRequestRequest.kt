package com.momsitter.presentation.care.dto

import com.momsitter.application.care.dto.UpdateCareRequestCommand


data class UpdateCareRequestRequest(
    val content: String
) {
    fun toCommand(requestId: Long, userId: Long): UpdateCareRequestCommand =
        UpdateCareRequestCommand(requestId = requestId, parentUserId = userId, content = content)
}
