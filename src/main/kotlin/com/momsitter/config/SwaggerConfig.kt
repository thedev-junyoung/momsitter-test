package com.momsitter.config

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType
import io.swagger.v3.oas.annotations.security.SecurityScheme
import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityRequirement as SwaggerSecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme as SwaggerSecurityScheme
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@SecurityScheme(
    name = "Authorization",
    type = SecuritySchemeType.HTTP,
    scheme = "bearer",
    bearerFormat = "JWT"
)
class SwaggerConfig {

    @Bean
    fun openAPI(): OpenAPI {
        return OpenAPI()
            .info(
                Info()
                    .title("맘시터 API")
                    .description("맘시터 프로젝트 API 명세서")
                    .version("v1.0.0")
            )
            .components(
                Components().addSecuritySchemes(
                    "Authorization", SwaggerSecurityScheme()
                        .type(SwaggerSecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT")
                )
            )
            .addSecurityItem(
                SwaggerSecurityRequirement().addList("Authorization")
            )
    }
}
