package com.momsitter.application.user.validator

import com.momsitter.domain.PasswordEncoder
import com.momsitter.domain.user.Gender
import com.momsitter.domain.user.Role
import com.momsitter.domain.user.User
import com.momsitter.domain.user.User.Companion.signUpAsParentOnly
import com.momsitter.domain.user.UserRepository
import com.momsitter.domain.user.exceptions.InvalidLoginException
import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.time.LocalDate

class LoginValidatorTest {

    private val userRepository = mockk<UserRepository>()
    private val passwordEncoder = mockk<PasswordEncoder>()
    private val loginValidator = LoginValidator(userRepository, passwordEncoder)

    @BeforeEach
    fun setUp() {
        loginTestUser()
    }
    private fun loginTestUser(
        username: String = "sitter123",
        password: String = "encoded-password"
    ): User {
        return signUpAsParentOnly(
            username = username,
            password = password,
            name = "테스트유저",
            birthDate = LocalDate.of(1990, 1, 1),
            gender = Gender.FEMALE,
            email = "test@example.com",
            role = Role.of("PARENT")
        )
    }

    @Test
    @DisplayName("유효한 아이디/비밀번호이면 User를 반환한다")
    fun valid_credentials_success() {
        val user = loginTestUser("sitter123", "encoded-password")
        every { userRepository.findByUsername("sitter123") } returns user
        every { passwordEncoder.matches("plain-password", "encoded-password") } returns true

        val result = loginValidator("sitter123", "plain-password")

        assertThat(result).isEqualTo(user)
    }

    @Test
    @DisplayName("존재하지 않는 아이디이면 예외가 발생한다")
    fun user_not_found() {
        every { userRepository.findByUsername("unknown") } returns null

        val exception = assertThrows(InvalidLoginException::class.java) {
            loginValidator("unknown", "any")
        }

        assertThat(exception.message).contains("존재하지 않는 사용자")
    }

    @Test
    @DisplayName("비밀번호 불일치 시 예외가 발생한다")
    fun password_mismatch() {
        val user = loginTestUser("sitter123", "encoded-password")
        every { userRepository.findByUsername("sitter123") } returns user
        every { passwordEncoder.matches("wrong", "encoded-password") } returns false

        val exception = assertThrows(InvalidLoginException::class.java) {
            loginValidator("sitter123", "wrong")
        }

        assertThat(exception.message).contains("비밀번호가 일치하지 않습니다")
    }
}
