package com.momsitter.support.initializer

import com.momsitter.domain.user.Role
import com.momsitter.domain.user.RoleRepository
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component

@Component
class RoleInitializer(
    private val roleRepository: RoleRepository
) : ApplicationRunner {

    override fun run(args: ApplicationArguments?) {
        val defaultRoles = listOf("SITTER", "PARENT")

        defaultRoles.forEach { roleName ->
            if (!roleRepository.existsByName(roleName)) {
                roleRepository.save(Role.of(name = roleName))
            }
        }
    }
}
