package com.momsitter.application.user.dto.result

data class LoginResult(
    val userId: Long,
    val username: String,
    val accessToken: String
)