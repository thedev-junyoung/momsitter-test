package com.momsitter.presentation.user.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.momsitter.common.ErrorCode
import com.momsitter.domain.user.Gender
import com.momsitter.presentation.user.dto.request.*
import com.momsitter.support.TestDataCleaner
import com.momsitter.support.TestLoginHelper
import org.assertj.core.api.Assertions.assertThat
import org.hamcrest.CoreMatchers.equalTo
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
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.patch
import org.springframework.test.web.servlet.post
import java.time.LocalDate

@SpringBootTest
@AutoConfigureMockMvc
@Import(TestLoginHelper::class)
class UserControllerIntegrationTest {


    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @Autowired
    lateinit var testDataCleaner: TestDataCleaner

    @Autowired
    lateinit var testLoginHelper: TestLoginHelper

    @AfterEach
    fun cleanUp() {
        listOf(
            "sitter123", "parent456", "parentNoChild",
            "parentToSitter", "sitterToParent", "sitterNoChild"
        ).forEach { username ->
            testDataCleaner.deleteUserCascade(username)
        }
    }



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
                            gender = Gender.FEMALE
                        ),
                        ChildRequest(
                            name = "아기2",
                            birthDate = LocalDate.of(2021, 6, 15),
                            gender = Gender.MALE
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

    @Nested
    @DisplayName("역할 확장")
    inner class ExtendRole {

        @Test
        @DisplayName("부모가 시터 역할로 확장 성공")
        fun extend_parent_to_sitter_success() {
            // given: 부모 회원가입
            val signUpRequest = SignupRequest(
                username = "parentToSitter",
                password = "pass123!",
                name = "부모시터전환",
                birthDate = LocalDate.of(1990, 1, 1),
                gender = "FEMALE",
                email = "ptos@example.com",
                roles = "PARENT",
                sitter = null,
                parent = ParentInfoRequest(children = emptyList())
            )

            val signUpResult = mockMvc.post("/api/v1/users") {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(signUpRequest)
            }.andExpect {
                status { isOk() }
            }.andReturn()

            objectMapper.readTree(signUpResult.response.contentAsString)["data"]["userId"].asLong()

            // when: 시터 역할 확장
            val extendRequest = ExtendToSitterRequest(
                minCareAge = 3,
                maxCareAge = 6,
                introduction = "저는 아이들을 좋아합니다."
            )

            val result = mockMvc.post("/api/v1/users/extend-role/sitter") {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(extendRequest)
                header("Authorization", "Bearer ${testLoginHelper.getAccessToken("parentToSitter", "pass123!")}")
            }.andExpect {
                status { isOk() }
            }.andReturn()

            // then
            val data = objectMapper.readTree(result.response.contentAsString)["data"]
            assertThat(data["sitterProfile"]).isNotNull
            assertThat(data["roles"].map { it.asText() }).contains("PARENT", "SITTER")
        }

        @Test
        @DisplayName("시터가 부모 역할로 확장 성공 - 자녀 포함")
        fun extend_sitter_to_parent_with_children() {
            // given: 시터 회원가입
            val signUpRequest = SignupRequest(
                username = "sitterToParent",
                password = "pass456!",
                name = "시터부모전환",
                birthDate = LocalDate.of(1988, 10, 10),
                gender = "MALE",
                email = "stop@example.com",
                roles = "SITTER",
                sitter = SitterInfoRequest(
                    minAge = 2,
                    maxAge = 5,
                    introduction = "시터로서의 경험이 풍부합니다."
                ),
                parent = null
            )

            val signUpResult = mockMvc.post("/api/v1/users") {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(signUpRequest)
            }.andExpect {
                status { isOk() }
            }.andReturn()

            objectMapper.readTree(signUpResult.response.contentAsString)["data"]["userId"].asLong()

            // when: 부모 역할 확장
            val extendRequest = ExtendToParentRequest(
                children = listOf(
                    ChildRequest("아기준호", LocalDate.of(2020, 4, 1), Gender.MALE),
                    ChildRequest("아기수아", LocalDate.of(2021, 9, 15), Gender.FEMALE)
                )
            )

            val result = mockMvc.post("/api/v1/users/extend-role/parent") {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(extendRequest)
                header("Authorization", "Bearer ${testLoginHelper.getAccessToken("sitterToParent", "pass456!")}")
            }.andExpect {
                status { isOk() }
            }.andReturn()

            // then
            val data = objectMapper.readTree(result.response.contentAsString)["data"]
            val children = data["children"]
            assertThat(children).hasSize(2)
            assertThat(data["roles"].map { it.asText() }).contains("SITTER", "PARENT")
        }

        @Test
        @DisplayName("시터가 부모 역할로 확장 성공 - 자녀 없이")
        fun extend_sitter_to_parent_without_children() {
            // given
            val signUpRequest = SignupRequest(
                username = "sitterNoChild",
                password = "nochild123!",
                name = "무자녀시터",
                birthDate = LocalDate.of(1983, 12, 25),
                gender = "FEMALE",
                email = "noc@example.com",
                roles = "SITTER",
                sitter = SitterInfoRequest(
                    minAge = 1,
                    maxAge = 4,
                    introduction = "아이를 정말 좋아해요."
                ),
                parent = null
            )

            val signUpResult = mockMvc.post("/api/v1/users") {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(signUpRequest)
            }.andExpect {
                status { isOk() }
            }.andReturn()

            objectMapper.readTree(signUpResult.response.contentAsString)["data"]["userId"].asLong()

            // when
            val extendRequest = ExtendToParentRequest(children = null)
            val token = testLoginHelper.getAccessToken("sitterNoChild", "nochild123!")
            val result = mockMvc.post("/api/v1/users/extend-role/parent") {
                header("Authorization", "Bearer $token")
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(extendRequest)
            }.andExpect {
                status { isOk() }
            }.andReturn()

            // then
            val data = objectMapper.readTree(result.response.contentAsString)["data"]

            assertThat(data["children"]).hasSize(0)
            assertThat(data["roles"].map { it.asText() }).contains("SITTER", "PARENT")
        }
    }

    @Nested
    @DisplayName("유저 정보 수정 및 비밀번호 변경")
    inner class UpdateUserInfoAndPassword {

        @Test
        @DisplayName("유저 정보 수정이 정상적으로 이루어진다")
        fun update_user_info_successfully() {
            // given
            val username = "updateUser"
            val password = "pass123!"

            testLoginHelper.createAndSaveParentUser(username, password)
            val token = testLoginHelper.getAccessToken(username, password)


            val updateRequest = UpdateUserInfoRequest(
                name = "변경된이름",
                email = "updated@example.com"
            )

            // when
            val result = mockMvc.patch("/api/v1/users/me/info") {
                header("Authorization", "Bearer $token")
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(updateRequest)
            }.andExpect {
                status { isNoContent() }
            }.andReturn()

            // then
            val responseJson = objectMapper.readTree(result.response.contentAsString)
            assertThat(responseJson.isEmpty).isTrue()

        }

        @Test
        @DisplayName("비밀번호 변경이 정상적으로 이루어진다")
        fun change_password_successfully() {
            // given
            val username = "pwChangeUser"
            val oldPassword = "oldPass123!"
            testLoginHelper.createAndSaveParentUser(username, oldPassword)

            val token = testLoginHelper.getAccessToken(username, oldPassword)
            val changePasswordRequest = ChangePasswordRequest(
                oldPassword = oldPassword,
                newPassword = "newPass456!"
            )

            // when
            mockMvc.patch("/api/v1/users/me/password") {
                header("Authorization", "Bearer $token")
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(changePasswordRequest)
            }.andExpect {
                status { isNoContent() }
            }

            // then: 새로운 비밀번호로 로그인 성공 확인
            val newToken = testLoginHelper.getAccessToken(username, "newPass456!")
            assertThat(newToken).isNotBlank()
        }



        @Test
        @DisplayName("비밀번호 변경 시 기존 비밀번호 불일치로 실패")
        fun change_password_with_wrong_old_password() {
            // given
            val username = "wrongPwUser"
            val correctPassword = "right123!"
            val wrongPassword = "wrong123!"

            testLoginHelper.createAndSaveParentUser(username, correctPassword)
            val token = testLoginHelper.getAccessToken(username, correctPassword)

            val request = ChangePasswordRequest(
                oldPassword = wrongPassword,
                newPassword = "new456!"
            )

            // when
            mockMvc.patch("/api/v1/users/me/password") {
                header("Authorization", "Bearer $token")
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(request)
            }.andExpect {
                status { is4xxClientError() }
                jsonPath("$.message") { value(equalTo(ErrorCode.INVALID_PASSWORD.message))}
            }
        }
    }
    @Nested
    @DisplayName("활성 역할 변경")
    inner class ChangeActiveRole {

        @Test
        @DisplayName("보유한 역할로 활성 역할 변경 성공")
        fun change_active_role_successfully() {
            // given: 부모로 가입 후 시터 역할 확장
            val username = "roleChangeUser"
            val password = "pass123!"
            val token = testLoginHelper.getAccessToken(username, password)

            // 시터 역할 확장
            val extendRequest = ExtendToSitterRequest(
                minCareAge = 3,
                maxCareAge = 6,
                introduction = "다양한 아이 돌봄 경험 있음"
            )
            mockMvc.post("/api/v1/users/extend-role/sitter") {
                header("Authorization", "Bearer $token")
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(extendRequest)
            }.andExpect {
                status { isOk() }
            }

            // when: 활성 역할을 SITTER로 변경
            val changeRoleRequest = ChangeRoleRequest(newRole = "SITTER")
            val result = mockMvc.patch("/api/v1/users/me/role") {
                header("Authorization", "Bearer $token")
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(changeRoleRequest)
            }.andExpect {
                status { isOk() }
                jsonPath("$.status") { value("SUCCESS") }
            }.andReturn()

            // then: 응답 데이터에서 activeRole 직접 확인
            val responseJson = objectMapper.readTree(result.response.contentAsString)
            val changedRole = responseJson["data"]?.asText()
            assertThat(changedRole).isEqualTo("SITTER")
        }


        @Test
        @DisplayName("보유하지 않은 역할로 변경 시 실패")
        fun change_active_role_to_unassigned_should_fail() {
            // given: 부모로 가입
            val username = "onlyParent"
            val password = "pass123!"
            val token = testLoginHelper.getAccessToken(username, password)

            // when: SITTER로 변경 시도 (보유하지 않음)
            val changeRoleRequest = ChangeRoleRequest(newRole = "SITTER")
            mockMvc.patch("/api/v1/users/me/role") {
                header("Authorization", "Bearer $token")
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(changeRoleRequest)
            }.andExpect {
                status { is4xxClientError() }
                jsonPath("$.message") { value(equalTo(ErrorCode.ROLE_NOT_FOUND.message)) }
            }
        }
    }

}
