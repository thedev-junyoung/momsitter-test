package com.momsitter.infrastructure

import com.momsitter.domain.user.User
import com.momsitter.domain.user.UserRepository
import com.momsitter.domain.user.UserRoleType
import com.momsitter.infrastructure.jpa.UserJpaRepository
import org.hibernate.Hibernate
import org.springframework.stereotype.Repository
import java.util.*

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

    override fun findById(userId: Long): Optional<User> {
        return userJpaRepository.findById(userId)
    }

    override fun deleteAll(): Int {
        return if (userJpaRepository.count() > 0) {
            userJpaRepository.deleteAll()
            1
        } else {
            0
        }
    }

    override fun findSitterUser(userId: Long): User? {
        return userJpaRepository.findSitterUser(userId)
    }

    override fun findParentUser(userId: Long): User? {
        // 1. parentProfile + children 먼저 fetch
        val user = userJpaRepository.findUserWithParentAndChildren(userId)
        // 2. careRequests는 명시적으로 사용 시점에 lazy load
        Hibernate.initialize(user?.parentProfile?.careRequests)
        return user
    }

    override fun getUserRole(userId: Long): UserRoleType? {
        return userJpaRepository.getUserRole(userId)
    }

    override fun findUserWithParentAndChildren(userId: Long): User? {
        return userJpaRepository.findUserWithParentAndChildren(userId)
    }

    override fun findUserWithSitterProfile(userId: Long): User? {
        return userJpaRepository.findUserWithSitterProfile(userId)
    }


}