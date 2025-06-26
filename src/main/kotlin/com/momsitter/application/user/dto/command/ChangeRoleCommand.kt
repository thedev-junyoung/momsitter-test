package com.momsitter.application.user.dto.command

import com.momsitter.domain.user.UserRoleType

data class ChangeRoleCommand(
    val userId: Long,
    val newRole: UserRoleType
) {
    companion object {
        fun of(userId: Long, newRole: String): ChangeRoleCommand {
            return ChangeRoleCommand(
                userId = userId,
                newRole = UserRoleType.valueOf(newRole.uppercase())
            )
        }
    }

}
