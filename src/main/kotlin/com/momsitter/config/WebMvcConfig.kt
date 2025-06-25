package com.momsitter.config

import com.momsitter.presentation.filter.JwtAuthenticationFilter
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class WebMvcConfig {

    @Bean
    fun jwtFilter(jwtAuthenticationFilter: JwtAuthenticationFilter): FilterRegistrationBean<JwtAuthenticationFilter> {
        return FilterRegistrationBean<JwtAuthenticationFilter>().apply {
            filter = jwtAuthenticationFilter
            addUrlPatterns("/api/v1/users/me", "/api/v1/care-requests/*") // 인증 필요한 경로만
            order = 1
        }
    }
}
