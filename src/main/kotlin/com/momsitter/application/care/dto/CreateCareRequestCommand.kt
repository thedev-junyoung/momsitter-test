package com.momsitter.application.care.dto

data class CreateCareRequestCommand(
    val parentUserId: Long,
    val content: String
)

