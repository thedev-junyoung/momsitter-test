package com.momsitter.application.user.service

import com.momsitter.application.user.dto.command.LoginCommand
import com.momsitter.domain.PasswordEncoder
import com.momsitter.domain.user.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDate


@SpringBootTest
class LoginServiceIntegrationTest {

    @Autowired
    lateinit var loginService: LoginService

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var passwordEncoder: PasswordEncoder




    @AfterEach
    fun tearDown() {
        userRepository.deleteAll()
    }

    @Test
    @DisplayName("로그인 성공 - DB에서 사용자 정보 조회 후 비밀번호 검증")
    fun login_success_from_db() {
        val rawPassword = "test1234!"
        val encoded = passwordEncoder.encode(rawPassword)

        val user = userRepository.save(
            TestUserFactory.createParentOnlyUser(
                username = "loginUser",
                password = encoded,
                name = "로그인유저",
                birthDate = LocalDate.of(1995, 5, 5),
                gender = Gender.FEMALE,
                email = "login@example.com",
            )
        )

        val result = loginService.login(LoginCommand("loginUser", rawPassword))
        assertThat(result.username).isEqualTo(user.username)
        assertThat(result.accessToken).isNotBlank
    }
}

