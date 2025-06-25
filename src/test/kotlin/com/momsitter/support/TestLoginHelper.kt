package com.momsitter.support

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post

@TestConfiguration
class TestLoginHelper(
    private val mockMvc: MockMvc,
    private val objectMapper: ObjectMapper,
) {

    fun getAccessToken(username: String, password: String): String {
        val loginRequest = mapOf("username" to username, "password" to password)

        val result = mockMvc.post("/api/v1/auth/login") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(loginRequest)
        }.andReturn()

        return objectMapper.readTree(result.response.contentAsString)["data"]["accessToken"].asText()
    }
}
