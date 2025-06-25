package com.momsitter.domain.sitter

import com.momsitter.domain.user.User

interface SitterProfileRepository {
    fun deleteByUser(user: User): Int
}