package com.momsitter.domain.user

interface RoleRepository {
    fun findByName(uppercase: String): Role?
}
