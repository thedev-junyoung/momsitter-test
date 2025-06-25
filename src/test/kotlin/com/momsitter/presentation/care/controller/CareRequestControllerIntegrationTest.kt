package com.momsitter.presentation.care.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.momsitter.domain.user.Gender
import com.momsitter.presentation.user.dto.*
import com.momsitter.support.TestLoginHelper
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.put
import java.time.LocalDate

@SpringBootTest
@AutoConfigureMockMvc
@Import(TestLoginHelper::class)
class CareRequestControllerIntegrationTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @Autowired
    lateinit var testLoginHelper: TestLoginHelper

    @Nested
    @DisplayName("돌봄 요청 API")
    inner class CareRequestAPI {

        @Test
        @DisplayName("돌봄 요청 생성 및 수정 성공")
        fun create_and_update_care_request() {
            // 회원가입
            val signUpRequest = SignupRequest(
                username = "parentForRequest",
                password = "pw12345!",
                name = "돌봄부모",
                birthDate = LocalDate.of(1980, 1, 1),
                gender = "FEMALE",
                email = "care@example.com",
                roles = "PARENT",
                sitter = null,
                parent = ParentInfoRequest(
                    children = listOf(
                        ChildRequest("아이1", LocalDate.of(2020, 1, 1), Gender.MALE)
                    )
                )
            )

            val signUpResult = mockMvc.post("/api/v1/users") {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(signUpRequest)
            }.andExpect {
                status { isOk() }
            }.andReturn()


            // 요청 생성
            val createRequest = mapOf("content" to "돌봄 요청합니다.")
            val createResult = mockMvc.post("/api/v1/care-requests") {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(createRequest)
                header("Authorization", "Bearer ${testLoginHelper.getAccessToken("parentForRequest", "pw12345!")}")

            }.andExpect {
                status { isOk() }
            }.andReturn()

            val requestId = objectMapper.readTree(createResult.response.contentAsString)["data"].asLong()
            assertThat(requestId).isGreaterThan(0L)

            // 요청 수정
            val updateRequest = mapOf("requestId" to requestId, "content" to "수정된 돌봄 요청입니다.")
            mockMvc.put("/api/v1/care-requests/$requestId") {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(updateRequest)
                header("Authorization", "Bearer ${testLoginHelper.getAccessToken("parentForRequest", "pw12345!")}")
            }.andExpect {
                status { isOk() }
            }
        }
    }
}
