package com.momsitter.infrastructure.jwt

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.util.*
import javax.crypto.spec.SecretKeySpec
import java.nio.charset.StandardCharsets

class JwtTokenProviderTest {

    private val jwtProperties = JwtProperties().apply {
        secret = "my-test-secret-key-that-is-long-enough-123456"
        expiration = 60000L // 60초
    }

    private val jwtTokenProvider = JwtTokenProvider(jwtProperties)

    @Test
    @DisplayName("JWT 토큰이 정상적으로 생성되고, 클레임이 포함되어야 한다")
    fun createToken_success() {
        // given
        val userId = 123L
        val username = "testuser"

        // when
        val token = jwtTokenProvider.createToken(userId, username)

        // then
        assertThat(token).isNotBlank
        assertThat(token.split(".")).hasSize(3) // JWT는 3부분 (header.payload.signature)

        val key = SecretKeySpec(
            jwtProperties.secret.toByteArray(StandardCharsets.UTF_8),
            SignatureAlgorithm.HS256.jcaName
        )

        val parsed = Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)

        assertThat(parsed.body.subject).isEqualTo(userId.toString())
        assertThat(parsed.body["username"]).isEqualTo(username)
        assertThat(parsed.body.expiration).isAfter(Date())
    }
}
