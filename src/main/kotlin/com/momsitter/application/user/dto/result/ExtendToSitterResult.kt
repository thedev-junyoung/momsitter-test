package com.momsitter.application.user.dto.result

import com.momsitter.domain.user.User

data class ExtendToSitterResult(
    val userId: Long,
    val roles: List<String>,
    val sitterProfile: SitterResult?
) {
    companion object {
        fun from(user: User): ExtendToSitterResult {
            return ExtendToSitterResult(
                userId = user.id,
                roles = user.roles.map { it.name },
                sitterProfile = user.sitterProfile?.let {
                    SitterResult.of(it.minCareAge, it.maxCareAge, it.introduction)
                }
            )
        }
    }

}
