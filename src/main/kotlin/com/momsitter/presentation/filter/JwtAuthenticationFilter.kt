package com.momsitter.presentation.filter

import com.momsitter.infrastructure.jwt.JwtTokenValidator
import jakarta.servlet.Filter
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component

@Component
class JwtAuthenticationFilter(
    private val jwtTokenValidator: JwtTokenValidator
) : Filter {

    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        val httpRequest = request as HttpServletRequest
        val httpResponse = response as HttpServletResponse

        val token = extractToken(httpRequest)

        if (token == null || !jwtTokenValidator.isValid(token)) {
            // 인증 실패 → 401 반환 후 필터 체인 종료
            httpResponse.status = HttpServletResponse.SC_UNAUTHORIZED
            httpResponse.contentType = "application/json"
            httpResponse.writer.write("""{"status":"UNAUTHORIZED","message":"유효하지 않은 토큰입니다."}""")
            return
        }

        // 인증 성공 → userId를 request에 바인딩
        val userId = jwtTokenValidator.extractUserId(token)
        httpRequest.setAttribute("userId", userId)

        chain.doFilter(request, response)
    }

    private fun extractToken(request: HttpServletRequest): String? {
        val authHeader = request.getHeader("Authorization")
        return if (authHeader?.startsWith("Bearer ") == true) {
            authHeader.substring(7)
        } else {
            null
        }
    }
}
