package com.momsitter.presentation.filter

import com.momsitter.infrastructure.jwt.JwtTokenValidator
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.junit.jupiter.api.BeforeEach
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
    fun `유효한 토큰이면 userId 를 setAttribute 하고 다음 필터로 진행`() {
        // given
        val request = mock(HttpServletRequest::class.java)
        val response = mock(HttpServletResponse::class.java)
        val chain = mock(FilterChain::class.java)

        `when`(request.getHeader("Authorization")).thenReturn("Bearer valid.token")
        `when`(jwtTokenValidator.isValid("valid.token")).thenReturn(true)
        `when`(jwtTokenValidator.extractUserId("valid.token")).thenReturn(123L)

        // when
        filter.doFilter(request, response, chain)

        // then
        verify(request).setAttribute("userId", 123L)
        verify(chain).doFilter(request, response)
        verify(response, never()).status = HttpServletResponse.SC_UNAUTHORIZED
    }

    @Test
    fun `토큰이 없거나 유효하지 않으면 401 응답`() {
        // given
        val request = mock(HttpServletRequest::class.java)
        val response = mock(HttpServletResponse::class.java)
        val writer = mock(PrintWriter::class.java)
        val chain = mock(FilterChain::class.java)

        `when`(request.getHeader("Authorization")).thenReturn("Bearer invalid.token")
        `when`(jwtTokenValidator.isValid("invalid.token")).thenReturn(false)
        `when`(response.writer).thenReturn(writer)

        // when
        filter.doFilter(request, response, chain)

        // then
        verify(response).status = HttpServletResponse.SC_UNAUTHORIZED
        verify(writer).write("""{"status":"UNAUTHORIZED","message":"유효하지 않은 토큰입니다."}""")
        verify(chain, never()).doFilter(request, response)
    }
}
