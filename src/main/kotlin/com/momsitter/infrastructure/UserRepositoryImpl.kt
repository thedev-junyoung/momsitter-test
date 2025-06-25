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
            1
        } else {
            0
        }
    }
    override fun findById(userId: Long): User? {
        return userJpaRepository.findById(userId).orElse(null)
    }

    override fun deleteAll(): Int {
        return if (userJpaRepository.count() > 0) {
            userJpaRepository.deleteAll()
            1
        } else {
            0
        }
    }

}