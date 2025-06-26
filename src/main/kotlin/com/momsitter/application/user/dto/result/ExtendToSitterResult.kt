package com.momsitter.application.user.dto.result

import com.momsitter.application.user.dto.result.ExtendToParentResult.ParentProfileResult
import com.momsitter.domain.user.User
import com.momsitter.domain.user.UserRole
import com.momsitter.domain.user.UserRoleType

data class ExtendToSitterResult(
    val userId: Long,
    val roles: Set<UserRoleType>,
    val sitterProfile: SitterResult?
) {
    companion object {
        fun from(user: User): ExtendToSitterResult {
            return ExtendToSitterResult(
                userId = user.id,
                roles = user.userRoles.map { it.role }.toSet(),
                sitterProfile = user.sitterProfile?.let {
                    SitterResult.of(it.minCareAge, it.maxCareAge, it.introduction)
                }
            )
        }
    }

}
