package com.momsitter.infrastructure.jpa

import com.momsitter.domain.user.User
import com.momsitter.domain.user.UserRepository
import org.springframework.stereotype.Repository

@Repository
class UserRepositoryImpl(): UserRepository {
    override fun save(user: User): User {
        TODO("Not yet implemented")
    }

    override fun existsByUsername(username: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun existsByEmail(email: String): Boolean {
        TODO("Not yet implemented")
    }
}