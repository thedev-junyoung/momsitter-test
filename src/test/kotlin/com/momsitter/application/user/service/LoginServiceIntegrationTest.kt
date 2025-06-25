package com.momsitter.application.user.service

import com.momsitter.application.user.dto.LoginCommand
import com.momsitter.domain.PasswordEncoder
import com.momsitter.domain.user.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
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
    fun login_success_from_db() {
        val rawPassword = "test1234!"
        val encoded = passwordEncoder.encode(rawPassword)

        val user = userRepository.save(
            User.signUpAsParentOnly(
                username = "loginUser",
                password = encoded,
                name = "로그인유저",
                birthDate = LocalDate.of(1995, 5, 5),
                gender = Gender.FEMALE,
                email = "login@example.com",
                role = UserRoleType.PARENT
            )
        )

        val result = loginService.login(LoginCommand("loginUser", rawPassword))
        assertThat(result.username).isEqualTo(user.username)
        assertThat(result.accessToken).isNotBlank
    }
}

