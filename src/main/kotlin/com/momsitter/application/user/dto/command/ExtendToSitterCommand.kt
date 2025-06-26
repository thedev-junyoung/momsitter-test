package com.momsitter.application.user.dto.command

data class ExtendToSitterCommand(
    val userId: Long,
    val minCareAge: Int,
    val maxCareAge: Int,
    val introduction: String
) {
    companion object {
        fun of(userId: Long, minCareAge: Int, maxCareAge: Int, introduction: String): ExtendToSitterCommand {
            return ExtendToSitterCommand(
                userId = userId,
                minCareAge = minCareAge,
                maxCareAge = maxCareAge,
                introduction = introduction
            )
        }
    }
}
