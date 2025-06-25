package com.momsitter.application.user.factory

import com.momsitter.domain.user.UserRoleType
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class UserFactoryResolverTest {

    @Test
    @DisplayName("지원하는 역할에 대해 올바른 팩토리를 반환한다")
    fun shouldReturnCorrectFactoryForSupportedRole() {
        // given
        val sitterFactory = mockk<UserFactory>()
        every { sitterFactory.supportedRole() } returns UserRoleType.SITTER

        val parentFactory = mockk<UserFactory>()
        every { parentFactory.supportedRole() } returns UserRoleType.PARENT

        val resolver = UserFactoryResolver(listOf(sitterFactory, parentFactory))

        // when
        val resolved = resolver.resolve(UserRoleType.SITTER)

        // then
        assertEquals(sitterFactory, resolved)
    }

    @Test
    @DisplayName("지원하지 않는 역할일 경우 예외가 발생한다")
    fun shouldThrowExceptionForUnsupportedRole() {
        // given
        val sitterFactory = mockk<UserFactory>()
        every { sitterFactory.supportedRole() } returns UserRoleType.SITTER

        val resolver = UserFactoryResolver(listOf(sitterFactory))

        // when & then
        val exception = assertThrows(IllegalArgumentException::class.java) {
            resolver.resolve(UserRoleType.PARENT)
        }
        assertTrue(exception.message!!.contains("지원하지 않는 역할"))
    }
}
