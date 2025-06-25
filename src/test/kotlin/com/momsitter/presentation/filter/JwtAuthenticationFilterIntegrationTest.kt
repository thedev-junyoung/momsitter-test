package com.momsitter.presentation.filter

import com.momsitter.domain.user.TestUserFactory
import com.momsitter.domain.user.UserRepository
import com.momsitter.infrastructure.jwt.JwtTokenProvider
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpHeaders
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("JwtAuthenticationFilter 통합 테스트")
class JwtAuthenticationFilterIntegrationTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var jwtTokenProvider: JwtTokenProvider

    lateinit var validToken: String

    @Autowired
    lateinit var userRepository: UserRepository

    @BeforeEach
    fun setup() {
        val user = TestUserFactory.sitterUser()
        val savedUser = userRepository.save(user)
        validToken = jwtTokenProvider.createToken(userId = savedUser.id, username = savedUser.username)
    }

    @AfterEach
    fun tearDown() {
        userRepository.deleteAll()
        validToken = ""
    }

    @Test
    @DisplayName("유효한 토큰이 있는 경우 인증된 요청으로 통과된다")
    fun should_pass_when_token_is_valid() {
        mockMvc.get("/api/v1/users/me") {
            header(HttpHeaders.AUTHORIZATION, "Bearer $validToken")
        }.andExpect {
            status { isOk() }
        }
    }

    @Test
    @DisplayName("토큰이 없는 경우 401 응답을 반환한다")
    fun should_return_401_when_token_is_missing() {
        mockMvc.get("/api/v1/users/me")
            .andExpect {
                status { isUnauthorized() }
            }
    }

    @Test
    @DisplayName("토큰이 유효하지 않은 경우 401 응답을 반환한다")
    fun should_return_401_when_token_is_invalid() {
        mockMvc.get("/api/v1/users/me") {
            header(HttpHeaders.AUTHORIZATION, "Bearer invalid.token.value")
        }.andExpect {
            status { isUnauthorized() }
        }
    }
}
