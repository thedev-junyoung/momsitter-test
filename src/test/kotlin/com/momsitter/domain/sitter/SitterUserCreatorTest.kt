package com.momsitter.domain.sitter

import com.momsitter.domain.user.Gender
import com.momsitter.domain.user.UserRoleType
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.time.LocalDate

class SitterUserCreatorTest {
    private val creator = SitterUserCreator()

    @Test
    @DisplayName("시터 사용자 생성 - 프로필 포함")
    fun create_sitter_user_with_profile() {
        // given
        val sitterInfo = SitterProfileInfo.of(
            minCareAge = 3,
            maxCareAge = 7,
            introduction = "아이들을 사랑하는 대학생입니다!"
        )

        // when
        val user = creator.createSitterUser(
            username = "sitter01",
            password = "encodedPw",
            name = "시터",
            birthDate = LocalDate.of(1990, 1, 1),
            gender = Gender.FEMALE,
            email = "sitter@example.com",
            sitterInfo = sitterInfo
        )

        // then
        assertThat(user.hasRole(UserRoleType.SITTER)).isTrue()
        assertThat(user.sitterProfile).isNotNull
        assertThat(user.sitterProfile!!.introduction).contains("아이들을 사랑")
    }
}