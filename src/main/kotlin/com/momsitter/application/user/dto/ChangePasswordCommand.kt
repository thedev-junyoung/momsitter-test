package com.momsitter.application.user.dto

data class ChangePasswordCommand(
    val userId: Long,
    val oldPassword: String,
    val newPassword: String
) {
    companion object {
        fun of(id: Long, oldPassword: String, newPassword: String): ChangePasswordCommand {
            return ChangePasswordCommand(
                userId = id,
                oldPassword = oldPassword,
                newPassword = newPassword
            )
        }
    }

}
