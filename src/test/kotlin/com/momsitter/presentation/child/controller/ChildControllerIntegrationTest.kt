package com.momsitter.presentation.child.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.momsitter.domain.user.Gender
import com.momsitter.presentation.child.dto.request.UpdateChildRequest
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
import java.time.LocalDate

@SpringBootTest
@AutoConfigureMockMvc
@Import(TestLoginHelper::class)
class ChildProfileControllerIntegrationTest {

    @Autowired lateinit var mockMvc: MockMvc
    @Autowired lateinit var objectMapper: ObjectMapper
    @Autowired lateinit var testLoginHelper: TestLoginHelper
    @Autowired lateinit var testDataCleaner: TestDataCleaner

    @AfterEach
    fun cleanUp() {
        testDataCleaner.deleteUserCascade("parent123")
    }

    @Nested
    @DisplayName("자녀 정보 수정")
    inner class UpdateChild {

        @Test
        @DisplayName("자녀 정보 수정 성공")
        fun update_child_success() {
            // given
            val username = "parent123"
            val password = "pass123!"
            val childId = testLoginHelper.createAndSaveParentWithChild(username, password)
            val token = testLoginHelper.getAccessToken(username, password)

            val request = UpdateChildRequest(
                name = "변경된아기",
                birthDate = LocalDate.of(2020, 6, 1),
                gender = Gender.FEMALE
            )

            // when & then
            mockMvc.patch("/api/v1/child/$childId/profile") {
                header("Authorization", "Bearer $token")
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(request)
            }.andExpect {
                status { isOk() }
                jsonPath("$.message") { value("자녀 정보가 수정되었습니다.") }
            }
        }

        @Test
        @DisplayName("존재하지 않는 자녀 ID로 수정 시 실패")
        fun update_child_fail_not_found() {
            // given
            val username = "parent123"
            val password = "pass123!"
            testLoginHelper.createAndSaveParentWithChild(username, password)
            val token = testLoginHelper.getAccessToken(username, password)
            val invalidChildId = 99999L

            val request = UpdateChildRequest(
                name = "없는아기",
                birthDate = LocalDate.of(2020, 1, 1),
                gender = Gender.MALE
            )

            // when & then
            mockMvc.patch("/api/v1/child/$invalidChildId/profile") {
                header("Authorization", "Bearer $token")
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(request)
            }.andExpect {
                status { isNotFound() }
                jsonPath("$.message") { value("자녀 정보가 존재하지 않습니다.") }
            }
        }
    }
}
