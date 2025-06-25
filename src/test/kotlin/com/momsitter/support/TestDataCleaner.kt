package com.momsitter.support

import com.momsitter.domain.user.UserRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Component

@Component
class TestDataCleaner(
    private val userRepository: UserRepository,
) {

    @Transactional
    fun deleteUserCascade(username: String) {
        userRepository.findByUsername(username)?.let { user ->
            userRepository.delete(user)
        }
    }
}
