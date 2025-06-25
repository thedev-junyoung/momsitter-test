package com.momsitter.application.user.dto

data class LoginResult(
    val userId: Long,
    val username: String,
    val accessToken: String
)