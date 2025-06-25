package com.momsitter

import jakarta.annotation.PreDestroy
import org.springframework.context.annotation.Configuration
import org.springframework.test.context.ActiveProfiles
import org.testcontainers.containers.MySQLContainer
import org.testcontainers.utility.DockerImageName

@Configuration
class TestcontainersConfiguration {

    @PreDestroy
    fun stopContainer() {
        if (mysqlContainer.isRunning) mysqlContainer.stop()
    }

    companion object {
        private val mysqlContainer: MySQLContainer<*> =
            MySQLContainer(DockerImageName.parse("mysql:8.0"))
                .withDatabaseName("momsitter")
                .withUsername("momsitter")
                .withPassword("momsitter")
                .withInitScript("init.sql")
                .apply { start() }

        init {
            System.setProperty(
                "spring.datasource.url",
                mysqlContainer.jdbcUrl + "?characterEncoding=UTF-8&serverTimezone=UTC"
            )
            System.setProperty("spring.datasource.username", mysqlContainer.username)
            System.setProperty("spring.datasource.password", mysqlContainer.password)
        }
    }
}