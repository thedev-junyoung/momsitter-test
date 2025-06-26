package com.momsitter.domain.user

import com.momsitter.common.BusinessException
import com.momsitter.common.ErrorCode
import com.momsitter.domain.child.ChildInfo
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.*
import java.time.LocalDate

@DisplayName("User 회원가입 도메인 테스트")
class UserTest {

    @Nested
    @DisplayName("시터 회원을 가입하는 경우")
    inner class SignUpSitterUserTest {

        @Test
        @DisplayName("자기소개와 케어 연령이 포함된 시터 프로필과 함께 회원을 생성한다")
        fun create_sitter_user_with_profile() {

            val user = TestUserFactory.createSitterUser(
                username = "wonderfulPark0206",
                password = "parak0206%^",
                name = "박시터",
                birthDate = LocalDate.of(1998, 2, 6),
                gender = Gender.FEMALE,
                email = "wonderfulPark0206@gmail.com",
                minCareAge = 3,
                maxCareAge = 5,
                introduction = "유아교육과를 전공중인 대학생 시터입니다! 사촌 동생들을 많이 돌본 경험이 있어서 아이랑 잘 놀아줄 수 있어요."
            )

            assertThat(user.isSitter()).isTrue()
            assertThat(user.sitterProfile?.minCareAge).isEqualTo(3)
        }
    }

    @Nested
    @DisplayName("자녀 없이 부모 회원을 가입하는 경우")
    inner class SignUpParentWithoutChildrenTest {

        @Test
        @DisplayName("빈 부모 프로필과 역할을 포함한 부모 회원을 생성한다")
        fun create_parent_user_without_children() {

            val user = TestUserFactory.createParentOnlyUser(
                username = "kimParent86",
                password = "86!@Kim",
                name = "박부모",
                birthDate = LocalDate.of(1986, 10, 19),
                gender = Gender.FEMALE,
                email = "kim86@gmail.com",
            )

            assertThat(user.isParent()).isTrue()
            assertThat(user.parentProfile?.children?.size).isEqualTo(0)
        }
    }

    @Nested
    @DisplayName("자녀 정보를 포함해 부모 회원을 가입하는 경우")
    inner class SignUpParentWithChildrenTest {

        @Test
        @DisplayName("부모 프로필과 자녀 정보를 함께 포함한 회원을 생성한다")
        fun create_parent_user_with_children() {
            val children = listOf(
                ChildInfo(name = "박아기", birthDate = LocalDate.of(2020, 7, 15), gender = Gender.FEMALE),
                ChildInfo(name = "김아기", birthDate = LocalDate.of(2019, 5, 22), gender = Gender.MALE)
            )

            val user = TestUserFactory.createParentWithChildrenUser(
                username = "kimParent86",
                password = "86!@Kim",
                name = "박부모",
                birthDate = LocalDate.of(1986, 10, 19),
                gender = Gender.FEMALE,
                email = "kim86@gmail.com",
                children = children,
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
        fun create_sitter_profile_if_not_present() {
            val user = TestUserFactory.parentOnlyUser()

            user.becomeSitter(3, 5, "소개")

            assertThat(user.sitterProfile).isNotNull
            assertThat(user.sitterProfile!!.minCareAge).isEqualTo(3)
        }

        @Test
        @DisplayName("이미 시터 프로필이 존재하면 생성하지 않는다")
        fun create_sitter_profile_if_already_exists() {
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
        fun create_parent_profile_if_not_present() {
            val user = TestUserFactory.parentOnlyUser()

            user.becomeParent()

            assertThat(user.parentProfile).isNotNull
        }

        @Test
        @DisplayName("이미 부모 프로필이 존재하면 생성하지 않는다")
        fun do_not_create_parent_profile_if_already_exists() {
            val user = TestUserFactory.parentOnlyUser()
            user.becomeParent()
            val original = user.parentProfile

            user.becomeParent()

            assertThat(user.parentProfile).isSameAs(original)
        }
    }

    @Nested
    @DisplayName("역할(Role) 관련 기능")
    inner class RoleTest {

        @Test
        @DisplayName("사용자가 특정 역할을 가지고 있는지 확인할 수 있다")
        fun check_user_role() {
            val user = TestUserFactory.parentOnlyUser()

            val hasParentRole = user.hasRole(UserRoleType.PARENT)
            val hasSitterRole = user.hasRole(UserRoleType.SITTER)

            assertThat(hasParentRole).isTrue()
            assertThat(hasSitterRole).isFalse()
        }

        @Test
        @DisplayName("새로운 역할을 추가할 수 있다")
        fun add_role() {
            val user = TestUserFactory.parentOnlyUser()

            user.addRole(UserRoleType.SITTER)

            assertThat(user.hasRole(UserRoleType.SITTER)).isTrue()
        }

        @Test
        @DisplayName("이미 존재하는 역할을 추가하려고 하면 예외가 발생한다")
        fun throws_exception_when_role_already_exists() {
            val user = TestUserFactory.sitterUser()

            val exception = assertThrows<BusinessException> {
                user.addRole(UserRoleType.SITTER)            }

            assertThat(exception.message).contains(ErrorCode.DUPLICATE_ROLE.message)
        }
    }
    @Nested
    @DisplayName("시터 역할 확장")
    inner class ExtendToSitterTest {

        @Test
        @DisplayName("부모 유저가 시터 역할을 확장할 수 있다")
        fun parent_user_can_extend_to_sitter() {
            // given
            val user = TestUserFactory.parentOnlyUser()

            // when
            user.extendToSitter(2, 6, "아이를 사랑하는 부모입니다.")

            // then
            assertThat(user.hasRole(UserRoleType.SITTER)).isTrue()
            assertThat(user.sitterProfile).isNotNull
            assertThat(user.sitterProfile!!.minCareAge).isEqualTo(2)
            assertThat(user.sitterProfile!!.maxCareAge).isEqualTo(6)
        }

        @Test
        @DisplayName("이미 시터 역할이 있는 경우 예외가 발생한다")
        fun throws_exception_when_already_sitter() {
            // given
            val user = TestUserFactory.sitterUser()

            // expect
            val exception = assertThrows<BusinessException> {
                user.extendToSitter(1, 3, "또 시터 하겠어요")
            }

            assertThat(exception.message).contains("이미 시터 역할을 가지고 있습니다.")
            assertThat(user.activeRole).isEqualTo(UserRoleType.SITTER)
        }
    }
    @Nested
    @DisplayName("부모 역할 확장")
    inner class ExtendToParentTest {

        @Test
        @DisplayName("시터 유저가 부모 역할을 자녀 없이 확장할 수 있다")
        fun sitter_user_can_extend_to_parent_without_children() {
            // given
            val user = TestUserFactory.sitterUser()

            // when
            user.extendToParent(emptyList())

            // then
            assertThat(user.hasRole(UserRoleType.PARENT)).isTrue()
            assertThat(user.parentProfile).isNotNull
            assertThat(user.parentProfile!!.children).isEmpty()
            assertThat(user.activeRole).isEqualTo(UserRoleType.PARENT)
        }

        @Test
        @DisplayName("시터 유저가 부모 역할을 자녀와 함께 확장할 수 있다")
        fun sitter_user_can_extend_to_parent_with_children() {
            // given
            val user = TestUserFactory.sitterUser()
            val children = listOf(
                ChildInfo("준호", LocalDate.of(2020, 6, 1), Gender.MALE),
                ChildInfo("수아", LocalDate.of(2021, 9, 15), Gender.FEMALE)
            )

            // when
            user.extendToParent(children)

            // then
            assertThat(user.hasRole(UserRoleType.PARENT)).isTrue()
            assertThat(user.parentProfile!!.children).hasSize(2)
            assertThat(user.parentProfile!!.children.map { it.name }).containsExactly("준호", "수아")
            assertThat(user.activeRole).isEqualTo(UserRoleType.PARENT)
        }
    }

    @Nested
    @DisplayName("수정 기능")
    inner class Update{


        @Test
        @DisplayName("사용자 정보를 수정할 수 있다")
        fun user_can_update() {
            val user = TestUserFactory.sitterUser()
            user.updateInfo("수정된 이름", "update@test.com")

            assertThat(user.name).isEqualTo("수정된 이름")
            assertThat(user.email).isEqualTo("update@test.com")
        }
        @Test
        @DisplayName("email 만 수정도 가능하다")
        fun user_can_update_email_only(){
            val user = TestUserFactory.parentOnlyUser()
            user.updateInfo(null,"updateEmail@test.com")
            assertThat(user.email).isEqualTo("updateEmail@test.com")
            assertThat(user.name).isEqualTo(user.name) // 이름은 변경되지 않아야 함
        }

        @Test
        @DisplayName("이름만 수정할 수 있다")
        fun user_can_update_name_only() {
            val user = TestUserFactory.sitterUser()
            user.updateInfo("새로운 이름", null)

            assertThat(user.name).isEqualTo("새로운 이름")
            assertThat(user.email).isEqualTo(user.email) // 이메일은 변경되지 않아야 함
        }

        @Test
        @DisplayName("비밀번호를 변경할 수 있다")
        fun user_can_change_password() {
            val user = TestUserFactory.sitterUser()
            val oldPassword = user.password
            val newPassword = "newSecurePassword123!"

            user.changePassword(newPassword)

            assertThat(user.password).isNotEqualTo(oldPassword) // 비밀번호가 변경되어야 함
            assertThat(user.password).isEqualTo(newPassword) // 새 비밀번호로 설정되어야 함
        }

        @Test
        @DisplayName("비밀번호 변경 시 빈 문자열은 허용되지 않는다")
        fun user_cannot_change_password_to_blank() {
            val user = TestUserFactory.sitterUser()
            val oldPassword = user.password

            val exception = assertThrows<BusinessException> {
                user.changePassword("")
            }

            assertThat(exception.message).contains("비밀번호는 빈 문자열일 수 없습니다.")
            assertThat(user.password).isEqualTo(oldPassword) // 비밀번호는 변경되지 않아야 함
        }

    }

    @Nested
    @DisplayName("User 도메인 - 활성 역할 변경 테스트")
    inner class ChangeActiveRoleTest {

        @Test
        @DisplayName("역할이 존재할 경우 활성 역할을 변경할 수 있다")
        fun change_active_role_successfully() {
            // given
            val user = TestUserFactory.sitterAndParentUser()  // ✅ 두 역할 다 가진 유저

            val newRole = UserRoleType.PARENT

            // when
            user.changeActiveRole(newRole)

            // then
            assertThat(user.activeRole).isEqualTo(newRole)
        }

        @Test
        @DisplayName("역할이 존재하지 않으면 활성 역할 변경 시 예외가 발생한다")
        fun change_active_role_throws_exception_if_role_not_exist() {
            // given
            val user = TestUserFactory.sitterUser()
            val newRole = UserRoleType.PARENT

            // when & then
            assertThatThrownBy { user.changeActiveRole(newRole) }
                .isInstanceOf(BusinessException::class.java)
                .hasMessageContaining("해당 역할이 없습니다.")
        }
    }

}
