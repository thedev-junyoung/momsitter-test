package com.momsitter.domain.parent

import com.momsitter.domain.child.ChildInfo
import com.momsitter.domain.user.Gender
import com.momsitter.domain.user.UserRoleType
import org.junit.jupiter.api.DisplayName
import kotlin.test.Test
import org.assertj.core.api.Assertions.assertThat
import java.time.LocalDate

class ParentUserCreatorTest {

    private val creator = ParentUserCreator()

    @Test
    @DisplayName("부모 사용자 생성 - 자녀 없음")
    fun create_parent_user_without_child(){
        // when
        val user = creator.createWithoutChildren(
            username = "parent01",
            password = "encodedPw",
            name = "부모",
            birthDate = LocalDate.of(1985, 5, 5),
            gender = Gender.MALE,
            email = "parent@example.com"
        )

        // then
        assertThat(user.parentProfile).isNotNull
        assertThat(user.parentProfile!!.children).isEmpty()
        assertThat(user.hasRole(UserRoleType.PARENT)).isTrue()
    }
    @Test
    @DisplayName("부모 사용자 생성 - 자녀 포함")
    fun create_parent_user_with_children() {
        // given
        val children = listOf(
            ChildInfo("아이1", LocalDate.of(2020, 6, 1), Gender.FEMALE),
            ChildInfo("아이2", LocalDate.of(2022, 8, 3), Gender.MALE)
        )

        // when
        val user = creator.createWithChildren(
            username = "parent02",
            password = "pw123!",
            name = "이부모",
            birthDate = LocalDate.of(1980, 4, 10),
            gender = Gender.FEMALE,
            email = "parent2@example.com",
            children = children
        )

        // then
        assertThat(user.parentProfile!!.children).hasSize(2)
        assertThat(user.parentProfile!!.children.map { it.name }).contains("아이1", "아이2")
        assertThat(user.hasRole(UserRoleType.PARENT)).isTrue()
    }
}