package com.momsitter.domain

interface PasswordEncoder {
    fun encode(raw: String): String
    fun matches(raw: String, encoded: String): Boolean
}