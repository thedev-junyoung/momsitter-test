package com.momsitter.presentation.sitter.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.momsitter.presentation.sitter.dto.request.UpdateSitterProfileRequest
import com.momsitter.support.TestDataCleaner
import com.momsitter.support.TestLoginHelper
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.patch

@SpringBootTest
@AutoConfigureMockMvc
@Import(TestLoginHelper::class)
class SitterProfileControllerIntegrationTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @Autowired
    lateinit var testLoginHelper: TestLoginHelper

    @Autowired
    lateinit var testDataCleaner: TestDataCleaner


    @AfterEach
    fun cleanUp() {
        testDataCleaner.deleteUserCascade("sitter123")
    }

    @Nested
    @DisplayName("시터 프로필 수정")
    inner class UpdateSitterProfile {

        @Test
        @DisplayName("시터 프로필 수정 성공")
        fun update_sitter_profile_success() {
            val username = "sitter123"
            val password = "pass123!"
            testLoginHelper.createAndSaveSitterUser(username, password)
            val token = testLoginHelper.getAccessToken(username, password)

            val request = UpdateSitterProfileRequest(2, 6, "새로운 자기소개입니다.")

            mockMvc.patch("/api/v1/sitter/profile") {
                header("Authorization", "Bearer $token")
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(request)
            }.andExpect {
                status { isOk() }
                jsonPath("$.message") { value("시터 프로필이 수정되었습니다.") }
            }
        }

        @Test
        @DisplayName("유효하지 않은 연령 입력으로 실패")
        fun update_sitter_profile_fail_due_to_invalid_age() {
            val username = "sitter123"
            val password = "pass123!"
            testLoginHelper.createAndSaveSitterUser(username, password)
            val token = testLoginHelper.getAccessToken(username, password)

            val request = UpdateSitterProfileRequest(6, 2, "잘못된 연령범위")

            mockMvc.patch("/api/v1/sitter/profile") {
                header("Authorization", "Bearer $token")
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(request)
            }.andExpect {
                status { isBadRequest() }
                jsonPath("$.message") { value("최소 나이는 최대 나이보다 작아야 합니다.") }
            }
        }
    }
}
