package com.momsitter.support

import com.fasterxml.jackson.databind.ObjectMapper
import com.momsitter.domain.PasswordEncoder
import com.momsitter.domain.sitter.SitterProfileInfo
import com.momsitter.domain.user.Gender
import com.momsitter.domain.user.TestUserFactory
import com.momsitter.domain.user.User
import com.momsitter.domain.user.UserRepository
import com.momsitter.domain.user.UserRoleType
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post
import java.time.LocalDate

@TestConfiguration
class TestLoginHelper(
    private val mockMvc: MockMvc,
    private val objectMapper: ObjectMapper,
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) {

    fun getAccessToken(username: String, password: String): String {
        val loginRequest = mapOf("username" to username, "password" to password)

        val result = mockMvc.post("/api/v1/auth/login") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(loginRequest)
        }.andReturn()

        return objectMapper.readTree(result.response.contentAsString)["data"]["accessToken"].asText()
    }

    fun createAndSaveParentUser(username: String, rawPassword: String): Long {
        val user = TestUserFactory.createParentOnlyUser(
            username = username,
            password = passwordEncoder.encode(rawPassword),
            name = "테스트부모",
            birthDate = LocalDate.of(1980, 1, 1),
            gender = Gender.FEMALE,
            email = "$username@example.com",
        )
        user.parentProfile!!.addChild("아기테스트", LocalDate.of(2020, 5, 5), Gender.MALE)
        return userRepository.save(user).id
    }

    fun createAndSaveParentWithChild(username: String, rawPassword: String): Long {
        val user = TestUserFactory.createParentOnlyUser(
            username = username,
            password = passwordEncoder.encode(rawPassword),
            name = "부모테스트",
            birthDate = LocalDate.of(1985, 3, 14),
            gender = Gender.FEMALE,
            email = "$username@example.com",
        )
        val child = user.parentProfile!!.addChild("아기테스트", LocalDate.of(2020, 1, 1), Gender.FEMALE)
        userRepository.save(user)
        return child.id
    }

    fun createAndSaveSitterUser(username: String, rawPassword: String): Long {
        val user = TestUserFactory.createSitterUser(
            username = username,
            password = passwordEncoder.encode(rawPassword),
            name = "시터테스트",
            birthDate = LocalDate.of(1990, 2, 2),
            gender = Gender.MALE,
            email = "$username@example.com",
        )
        return userRepository.save(user).id
    }

}
