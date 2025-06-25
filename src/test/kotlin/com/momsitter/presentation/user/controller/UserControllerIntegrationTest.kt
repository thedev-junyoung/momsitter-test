package com.momsitter.presentation.user.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.momsitter.presentation.user.dto.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post
import java.time.LocalDate

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerIntegrationTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var objectMapper: ObjectMapper


    @Nested
    @DisplayName("회원가입")
    inner class SignUp {
        @Test
        @DisplayName("시터 회원가입 성공")
        fun signup_sitter_success() {
            // given
            val request = SignupRequest(
                username = "sitter123",
                password = "pass123!",
                name = "시터유저",
                birthDate = LocalDate.of(1995, 5, 5),
                gender = "FEMALE",
                email = "sitter@example.com",
                roles = "SITTER",
                sitter = SitterInfoRequest(
                    minAge = 2,
                    maxAge = 6,
                    introduction = "아이를 잘 돌보는 시터입니다."
                ),
                parent = null
            )

            // when
            val result = mockMvc.post("/api/v1/users") {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(request)
            }.andExpect {
                status { isOk() }
            }.andReturn()

            // then
            val json = objectMapper.readTree(result.response.contentAsString)["data"]
            val userId = json["userId"].asLong()

            assertThat(userId).isGreaterThan(0L)
            assertThat(json["username"].asText()).isEqualTo("sitter123")
            assertThat(json["role"].asText()).isEqualTo("SITTER")
        }

        @Test
        @DisplayName("부모 회원가입 성공")
        fun signup_parent_success() {
            // given
            val request = SignupRequest(
                username = "parent456",
                password = "pass456!",
                name = "부모유저",
                birthDate = LocalDate.of(1985, 8, 20),
                gender = "MALE",
                email = "parent@example.com",
                roles = "PARENT",
                sitter = null,
                parent = ParentInfoRequest(
                    children = listOf(
                        ChildRequest(
                            name = "아기1",
                            birthDate = LocalDate.of(2020, 1, 1),
                            gender = "FEMALE"
                        ),
                        ChildRequest(
                            name = "아기2",
                            birthDate = LocalDate.of(2021, 6, 15),
                            gender = "MALE"
                        )
                    )
                )
            )

            // when
            val result = mockMvc.post("/api/v1/users") {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(request)
            }.andExpect {
                status { isOk() }
            }.andReturn()

            // then
            val json = objectMapper.readTree(result.response.contentAsString)["data"]
            val userId = json["userId"].asLong()

            assertThat(userId).isGreaterThan(0L)
            assertThat(json["username"].asText()).isEqualTo("parent456")
            assertThat(json["role"].asText()).isEqualTo("PARENT")
        }

        @Test
        @DisplayName("부모 회원가입 성공 - 자녀 정보 없이도 가입 가능")
        fun signup_parent_without_children_success() {
            // given
            val request = SignupRequest(
                username = "parentNoChild",
                password = "securePass123!",
                name = "부모무자녀",
                birthDate = LocalDate.of(1982, 3, 14),
                gender = "FEMALE",
                email = "parent.no.child@example.com",
                roles = "PARENT",
                sitter = null,
                parent = ParentInfoRequest(
                    children = emptyList() // 핵심: 자녀 없음
                )
            )

            // when
            val result = mockMvc.post("/api/v1/users") {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(request)
            }.andExpect {
                status { isOk() }
            }.andReturn()

            // then
            val json = objectMapper.readTree(result.response.contentAsString)["data"]
            val userId = json["userId"].asLong()

            assertThat(userId).isGreaterThan(0L)
            assertThat(json["username"].asText()).isEqualTo("parentNoChild")
            assertThat(json["role"].asText()).isEqualTo("PARENT")
        }
    }



}
