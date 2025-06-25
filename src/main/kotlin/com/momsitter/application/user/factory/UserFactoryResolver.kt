package com.momsitter.application.user.factory

import org.springframework.stereotype.Component

@Component
class UserFactoryResolver(
    factories: List<UserFactory>
) {
    private val factoryMap: Map<String, UserFactory> =
        factories.associateBy { it.supportedRole() }

    fun resolve(role: String): UserFactory {
        return factoryMap[role.uppercase()]
            ?: throw IllegalArgumentException("지원하지 않는 역할입니다: $role")
    }
}
