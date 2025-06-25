package com.momsitter.domain.parent

import com.momsitter.domain.user.User

interface ParentProfileRepository {
    fun deleteByUser(user: User): Int
}