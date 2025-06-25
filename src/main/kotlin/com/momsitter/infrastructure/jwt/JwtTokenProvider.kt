package com.momsitter.infrastructure.jwt

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets
import java.util.*
import javax.crypto.spec.SecretKeySpec

@Component
class JwtTokenProvider(
    private val jwtProperties: JwtProperties
) {
    fun createToken(userId: Long, username: String): String {
        val now = Date()
        val expiry = Date(now.time + jwtProperties.expiration)
        val key = SecretKeySpec(
            jwtProperties.secret.toByteArray(StandardCharsets.UTF_8),
            SignatureAlgorithm.HS256.jcaName
        )
        return Jwts.builder()
            .setSubject(userId.toString())
            .claim("username", username)
            .setIssuedAt(now)
            .setExpiration(expiry)
            .signWith(key)
            .compact()
    }
}
