package com.momsitter.infrastructure

import com.momsitter.domain.user.Role
import com.momsitter.domain.user.RoleRepository
import com.momsitter.infrastructure.jpa.RoleJpaRepository
import org.springframework.stereotype.Repository

@Repository
class RoleRepositoryImpl(
    private val roleJpaRepository: RoleJpaRepository
) : RoleRepository {
    override fun findByName(uppercase: String): Role? {
        return roleJpaRepository.findByName(uppercase)
    }

    override fun existsByName(roleName: String): Boolean {
        return roleJpaRepository.existsByName(roleName)
    }

    override fun save(role: Role): Role {
        return roleJpaRepository.save(role)
    }
}