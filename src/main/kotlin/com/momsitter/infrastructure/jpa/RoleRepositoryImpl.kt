package com.momsitter.infrastructure.jpa

import com.momsitter.domain.user.Role
import com.momsitter.domain.user.RoleRepository
import org.springframework.stereotype.Repository

@Repository
class RoleRepositoryImpl(): RoleRepository {
    override fun findByName(uppercase: String): Role? {
        TODO("Not yet implemented")
    }
}