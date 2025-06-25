package com.momsitter.domain.user

interface UserRepository {
    fun save(user: User): User
    fun existsByUsername(username: String): Boolean
    fun existsByEmail(email: String): Boolean
    fun findByUsername(username: String): User?
    fun delete(user: User): Int
    fun findById(userId: Long): User?
}
