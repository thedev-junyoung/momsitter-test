package com.momsitter.application.user.factory

import com.momsitter.domain.user.UserRoleType
import org.springframework.stereotype.Component

@Component
class UserFactoryResolver(
    factories: List<UserFactory>
) {
    private val factoryMap: Map<UserRoleType, UserFactory> =
        factories.associateBy { it.supportedRole() }

    fun resolve(role: UserRoleType): UserFactory {
        return factoryMap[role]
            ?: throw IllegalArgumentException("지원하지 않는 역할입니다: $role")
    }
}
