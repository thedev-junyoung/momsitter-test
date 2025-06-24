package com.momsitter.config

import com.momsitter.domain.PasswordEncoder
import com.momsitter.infrastructure.BCryptPasswordEncoderAdapter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SecurityConfig {

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoderAdapter()
    }
}
