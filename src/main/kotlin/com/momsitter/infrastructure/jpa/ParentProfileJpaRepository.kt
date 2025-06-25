package com.momsitter.infrastructure.jpa

import com.momsitter.domain.parent.ParentProfile
import com.momsitter.domain.user.User
import org.springframework.data.jpa.repository.JpaRepository

interface ParentProfileJpaRepository : JpaRepository<ParentProfile, Long> {
    fun deleteByUser(user: User): Int
}
