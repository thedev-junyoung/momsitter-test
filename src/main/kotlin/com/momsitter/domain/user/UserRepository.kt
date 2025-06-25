package com.momsitter.domain.user

import java.util.*


interface UserRepository {
    fun save(user: User): User
    fun existsByUsername(username: String): Boolean
    fun existsByEmail(email: String): Boolean
    fun findByUsername(username: String): User?
    fun delete(user: User): Int
    fun findById(userId: Long): Optional<User>
    fun deleteAll() : Int
    fun findSitterUser(userId: Long): User?
    fun findParentUser(userId: Long): User?
    fun getUserRole(userId: Long): UserRoleType?
}
