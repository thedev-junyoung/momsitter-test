package com.momsitter.application.sitter.dto

data class UpdateSitterProfileCommand(
    val userId: Long,
    val minCareAge: Int?,
    val maxCareAge: Int?,
    val introduction: String?
)
