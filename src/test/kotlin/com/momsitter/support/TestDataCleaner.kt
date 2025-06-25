package com.momsitter.support

import com.momsitter.domain.parent.ParentProfileRepository
import com.momsitter.domain.sitter.SitterProfileRepository
import com.momsitter.domain.user.UserRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Component

@Component
class TestDataCleaner(
    private val userRepository: UserRepository,
    private val sitterProfileRepository: SitterProfileRepository,
    private val parentProfileRepository: ParentProfileRepository
) {

    @Transactional
    fun deleteUserCascade(username: String) {
        userRepository.findByUsername(username)?.let { user ->
            sitterProfileRepository.deleteByUser(user)
            parentProfileRepository.deleteByUser(user)
            userRepository.delete(user)
        }
    }
}
