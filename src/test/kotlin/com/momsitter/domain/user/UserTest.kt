package com.momsitter.domain.user

import com.momsitter.domain.child.ChildInfo
import com.momsitter.domain.sitter.SitterProfileInfo
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.*
import java.time.LocalDate

@DisplayName("User 회원가입 도메인 테스트")
class UserTest {

    private fun createRole(name: String): Role = Role.of(name)

    @Nested
    @DisplayName("시터 회원을 가입하는 경우")
    inner class SignUpSitterUserTest {

        @Test
        @DisplayName("자기소개와 케어 연령이 포함된 시터 프로필과 함께 회원을 생성한다")
        fun `creates sitter user with sitter profile`() {
            val role = createRole("SITTER")
            val sitterInfo = SitterProfileInfo(
                minCareAge = 3,
                maxCareAge = 5,
                introduction = "유아교육과를 전공중인 대학생 시터입니다! 사촌 동생들을 많이 돌본 경험이 있어서 아이랑 잘 놀아줄 수 있어요."
            )

            val user = User.signUpAsSitter(
                username = "wonderfulPark0206",
                password = "parak0206%^",
                name = "박시터",
                birthDate = LocalDate.of(1998, 2, 6),
                gender = Gender.FEMALE,
                email = "wonderfulPark0206@gmail.com",
                role = role,
                sitterInfo = sitterInfo
            )

            assertThat(user.isSitter()).isTrue()
            assertThat(user.userRoles.first().role.name).isEqualTo("SITTER")
            assertThat(user.sitterProfile?.minCareAge).isEqualTo(3)
        }
    }

    @Nested
    @DisplayName("자녀 없이 부모 회원을 가입하는 경우")
    inner class SignUpParentWithoutChildrenTest {

        @Test
        @DisplayName("빈 부모 프로필과 역할을 포함한 부모 회원을 생성한다")
        fun `creates parent user with empty parent profile`() {
            val role = createRole("PARENT")

            val user = User.signUpAsParentOnly(
                username = "kimParent86",
                password = "86!@Kim",
                name = "박부모",
                birthDate = LocalDate.of(1986, 10, 19),
                gender = Gender.FEMALE,
                email = "kim86@gmail.com",
                role = role
            )

            assertThat(user.isParent()).isTrue()
            assertThat(user.userRoles.first().role.name).isEqualTo("PARENT")
            assertThat(user.parentProfile?.children?.size).isEqualTo(0)
        }
    }

    @Nested
    @DisplayName("자녀 정보를 포함해 부모 회원을 가입하는 경우")
    inner class SignUpParentWithChildrenTest {

        @Test
        @DisplayName("부모 프로필과 자녀 정보를 함께 포함한 회원을 생성한다")
        fun `creates parent user with children`() {
            val role = createRole("PARENT")
            val children = listOf(
                ChildInfo(name = "박아기", birthDate = LocalDate.of(2020, 7, 15), gender = Gender.FEMALE),
                ChildInfo(name = "김아기", birthDate = LocalDate.of(2019, 5, 22), gender = Gender.MALE)
            )

            val user = User.signUpAsParentWithChildren(
                username = "kimParent86",
                password = "86!@Kim",
                name = "박부모",
                birthDate = LocalDate.of(1986, 10, 19),
                gender = Gender.FEMALE,
                email = "kim86@gmail.com",
                role = role,
                children = children
            )

            assertThat(user.isParent()).isTrue()
            assertThat(user.parentProfile?.children?.size).isEqualTo(2)
            assertThat(user.parentProfile?.children?.get(0)?.name).isEqualTo("박아기")
        }
    }

    @Nested
    @DisplayName("시터 프로필 생성을 요청하는 경우")
    inner class BecomeSitterTest {

        @Test
        @DisplayName("시터 프로필이 없을 경우 새로 생성된다")
        fun `creates sitter profile if not present`() {
            val user = TestUserFactory.parentOnlyUser()

            user.becomeSitter(3, 5, "소개")

            assertThat(user.sitterProfile).isNotNull
            assertThat(user.sitterProfile!!.minCareAge).isEqualTo(3)
        }

        @Test
        @DisplayName("이미 시터 프로필이 존재하면 생성하지 않는다")
        fun `does not overwrite existing sitter profile`() {
            val user = TestUserFactory.sitterUser()
            val original = user.sitterProfile

            user.becomeSitter(1, 2, "변경 시도")

            assertThat(user.sitterProfile).isSameAs(original)
        }
    }

    @Nested
    @DisplayName("부모 프로필 생성을 요청하는 경우")
    inner class BecomeParentTest {

        @Test
        @DisplayName("부모 프로필이 없을 경우 새로 생성된다")
        fun `creates parent profile if not present`() {
            val user = TestUserFactory.parentOnlyUser()

            user.becomeParent()

            assertThat(user.parentProfile).isNotNull
        }

        @Test
        @DisplayName("이미 부모 프로필이 존재하면 생성하지 않는다")
        fun `does not overwrite existing parent profile`() {
            val user = TestUserFactory.parentOnlyUser()
            user.becomeParent()
            val original = user.parentProfile

            user.becomeParent()

            assertThat(user.parentProfile).isSameAs(original)
        }
    }
}
