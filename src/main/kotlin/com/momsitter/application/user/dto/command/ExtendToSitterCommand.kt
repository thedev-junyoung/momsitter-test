package com.momsitter.application.user.dto.command

data class ExtendToSitterCommand(
    val userId: Long,
    val minCareAge: Int,
    val maxCareAge: Int,
    val introduction: String
)
