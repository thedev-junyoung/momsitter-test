package com.momsitter.presentation.filter

import com.momsitter.infrastructure.jwt.JwtTokenValidator
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import java.io.PrintWriter

class JwtAuthenticationFilterTest {

    private lateinit var jwtTokenValidator: JwtTokenValidator
    private lateinit var filter: JwtAuthenticationFilter

    @BeforeEach
    fun setup() {
        jwtTokenValidator = mock(JwtTokenValidator::class.java)
        filter = JwtAuthenticationFilter(jwtTokenValidator)
    }

    @Test
    @DisplayName("유효한 토큰이면 userId 를 setAttribute 하고 다음 필터로 진행")
    fun should_set_userId_and_continue_chain_when_token_is_valid() {
        val request = mock(HttpServletRequest::class.java)
        val response = mock(HttpServletResponse::class.java)
        val chain = mock(FilterChain::class.java)

        `when`(request.getRequestURI()).thenReturn("/api/v1/protected-endpoint")
        `when`(request.method).thenReturn("GET")
        `when`(request.getHeader("Authorization")).thenReturn("Bearer valid.token")
        `when`(jwtTokenValidator.isValid("valid.token")).thenReturn(true)
        `when`(jwtTokenValidator.extractUserId("valid.token")).thenReturn(123L)

        filter.doFilter(request, response, chain)

        verify(request).setAttribute("userId", 123L)
        verify(chain).doFilter(request, response)
        verify(response, never()).status = HttpServletResponse.SC_UNAUTHORIZED
    }

    @Test
    @DisplayName("토큰이 없거나 유효하지 않으면 401 응답")
    fun should_return_401_when_token_is_invalid() {
        val request = mock(HttpServletRequest::class.java)
        val response = mock(HttpServletResponse::class.java)
        val writer = mock(PrintWriter::class.java)
        val chain = mock(FilterChain::class.java)

        `when`(request.getRequestURI()).thenReturn("/api/v1/protected-endpoint")
        `when`(request.method).thenReturn("GET")
        `when`(request.getHeader("Authorization")).thenReturn("Bearer invalid.token")
        `when`(jwtTokenValidator.isValid("invalid.token")).thenReturn(false)
        `when`(response.writer).thenReturn(writer)

        filter.doFilter(request, response, chain)

        verify(response).status = HttpServletResponse.SC_UNAUTHORIZED
        verify(writer).write("""{"status":"UNAUTHORIZED","message":"유효하지 않은 토큰입니다."}""")
        verify(chain, never()).doFilter(request, response)
    }
}
