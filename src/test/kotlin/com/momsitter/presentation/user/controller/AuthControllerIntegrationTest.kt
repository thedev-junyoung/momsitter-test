package com.momsitter.presentation.user.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.momsitter.presentation.user.dto.LoginRequest
import com.momsitter.support.TestDataCleaner
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("AuthController 통합 테스트")
class AuthControllerIntegrationTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @Autowired
    lateinit var testDataCleaner: TestDataCleaner

    val userName = "wonderfulPark0206"
    val userPassword = "parak0206%^"

    @BeforeEach
    fun setUp() {
        // 회원가입 요청 객체
        val signupRequest = mapOf(
            "username" to userName,
            "password" to userPassword,
            "name" to "박시터",
            "birthDate" to "1998-02-06",
            "gender" to "FEMALE",
            "email" to "wonderfulPark0206@gmail.com",
            "roles" to "SITTER",
            "sitter" to mapOf(
                "minAge" to 3,
                "maxAge" to 5,
                "introduction" to "테스트용 시터입니다."
            ),
            "parent" to null
        )

        mockMvc.post("/api/v1/users") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(signupRequest)
        }.andExpect {
            status { isOk() }
        }
    }

    @AfterEach
    fun tearDown() {
        // 테스트에서 사용한 사용자 데이터 삭제
        testDataCleaner.deleteUserCascade(userName)
    }

    @Test
    @DisplayName("로그인 성공")
    fun login_success() {
        // given: 먼저 테스트 사용자 생성되어 있어야 함 (혹은 BeforeEach로 시드 처리 가능)
        val request = LoginRequest(
            username = userName,
            password = userPassword
        )

        // when
        val result = mockMvc.post("/api/v1/auth/login") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(request)
        }.andExpect {
            status { isOk() }
        }.andReturn()

        // then
        val json = objectMapper.readTree(result.response.contentAsString)["data"]
        assertThat(json["userId"].asLong()).isGreaterThan(0L)
        assertThat(json["username"].asText()).isEqualTo(userName)
        assertThat(json["accessToken"].asText()).isNotBlank()
    }
}
