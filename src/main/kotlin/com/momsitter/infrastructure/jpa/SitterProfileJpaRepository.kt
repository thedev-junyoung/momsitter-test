package com.momsitter.infrastructure.jpa

import com.momsitter.domain.sitter.SitterProfile
import com.momsitter.domain.user.User
import org.springframework.data.jpa.repository.JpaRepository

interface SitterProfileJpaRepository : JpaRepository<SitterProfile, Long> {
    fun deleteByUser(user: User): Int
}
