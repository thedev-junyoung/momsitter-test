package com.momsitter.application.user.dto

data class ChangePasswordCommand(
    val userId: Long,
    val oldPassword: String,
    val newPassword: String
) {
    companion object {
        fun of(userId: Long, oldPassword: String, newPassword: String): ChangePasswordCommand {
            return ChangePasswordCommand(
                userId = userId,
                oldPassword = oldPassword,
                newPassword = newPassword
            )
        }
    }

}
