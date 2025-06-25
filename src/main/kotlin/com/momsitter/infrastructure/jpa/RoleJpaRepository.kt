package com.momsitter.infrastructure.jpa

import com.momsitter.domain.user.Role
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RoleJpaRepository : JpaRepository<Role, Long> {
    fun findByName(name: String): Role?
}
