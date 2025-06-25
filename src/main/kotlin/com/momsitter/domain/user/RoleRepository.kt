package com.momsitter.domain.user

interface RoleRepository {
    fun findByName(uppercase: String): Role?
    fun existsByName(roleName: String): Boolean
    fun save(role: Role): Role
}
