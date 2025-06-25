package com.momsitter.application.user.service

data class UpdateUserInfoCommand(
    val userId: Long,
    val email: String? = null,
    val name: String? = null,

) {
    companion object {
        fun of(userId: Long, name: String?, email: String?): UpdateUserInfoCommand {
            return UpdateUserInfoCommand(userId = userId, name = name, email = email)
        }
    }

}
