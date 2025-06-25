package com.momsitter.application.user.service

data class UpdateUserInfoCommand(
    val userId: Long,
    val email: String? = null,
    val name: String? = null,

) {

}
