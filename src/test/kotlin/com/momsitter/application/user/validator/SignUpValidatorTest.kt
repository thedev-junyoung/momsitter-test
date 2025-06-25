package com.momsitter.application.user.validator

import com.momsitter.domain.user.UserRepository
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class SignUpValidatorTest {

    private val userRepository = mockk<UserRepository>()
    private val validator = SignUpValidator(userRepository)

    @Test
    @DisplayName("아이디가 중복되면 예외가 발생한다")
    fun shouldThrowExceptionIfUsernameIsDuplicated() {
        // given
        every { userRepository.existsByUsername("duplicatedUser") } returns true
        every { userRepository.existsByEmail(any()) } returns false

        // when & then
        val ex = assertThrows(IllegalArgumentException::class.java) {
            validator.validate("duplicatedUser", "user@email.com")
        }

        assertEquals("이미 사용 중인 아이디입니다.", ex.message)
    }

    @Test
    @DisplayName("이메일이 중복되면 예외가 발생한다")
    fun shouldThrowExceptionIfEmailIsDuplicated() {
        every { userRepository.existsByUsername(any()) } returns false
        every { userRepository.existsByEmail("used@email.com") } returns true

        val ex = assertThrows(IllegalArgumentException::class.java) {
            validator.validate("newUser", "used@email.com")
        }

        assertEquals("이미 사용 중인 이메일입니다.", ex.message)
    }

    @Test
    @DisplayName("중복이 없으면 예외가 발생하지 않는다")
    fun shouldNotThrowExceptionIfNoDuplication() {
        every { userRepository.existsByUsername("newUser") } returns false
        every { userRepository.existsByEmail("new@email.com") } returns false

        assertDoesNotThrow {
            validator.validate("newUser", "new@email.com")
        }
    }
}
