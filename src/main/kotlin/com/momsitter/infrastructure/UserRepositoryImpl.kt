package com.momsitter.infrastructure

import com.momsitter.domain.user.User
import com.momsitter.domain.user.UserRepository
import com.momsitter.infrastructure.jpa.UserJpaRepository
import org.springframework.stereotype.Repository

@Repository
class UserRepositoryImpl(
    private val userJpaRepository: UserJpaRepository
): UserRepository {

    override fun save(user: User): User {
        return userJpaRepository.save(user)
    }

    override fun existsByUsername(username: String): Boolean {
        return userJpaRepository.existsByUsername(username)
    }

    override fun existsByEmail(email: String): Boolean {
        return userJpaRepository.existsByEmail(email)
    }

    override fun findByUsername(username: String): User? {
        return userJpaRepository.findByUsername(username)
    }

    override fun delete(user: User): Int {
        return if (userJpaRepository.existsById(user.id)) {
            userJpaRepository.delete(user)
            1 // Return 1 to indicate successful deletion
        } else {
            0 // Return 0 if the user does not exist
        }
    }
    override fun findById(userId: Long): User? {
        return userJpaRepository.findById(userId).orElse(null)
    }

}