package com.momsitter.application.care.dto

data class UpdateCareRequestCommand(
    val requestId: Long,
    val parentUserId: Long,
    val content: String
)
