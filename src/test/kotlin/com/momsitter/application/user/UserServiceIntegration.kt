    package com.momsitter.application.user

    import com.momsitter.application.user.service.UserService
    import com.momsitter.application.user.dto.SignUpCommand
    import com.momsitter.application.user.dto.SignUpResult
    import com.momsitter.domain.child.ChildInfo
    import com.momsitter.domain.parent.ParentProfileInfo
    import com.momsitter.domain.sitter.SitterProfileInfo
    import com.momsitter.domain.user.Gender
    import com.momsitter.support.TestDataCleaner
    import org.assertj.core.api.Assertions.assertThat
    import org.junit.jupiter.api.*
    import org.springframework.beans.factory.annotation.Autowired
    import org.springframework.boot.test.context.SpringBootTest
    import java.time.LocalDate
    import kotlin.collections.emptyList

    @SpringBootTest
    class UserServiceIntegration(
        @Autowired
        private val userService: UserService,

        @Autowired
        private val testDataCleaner: TestDataCleaner
    ) {
        @AfterEach
        fun cleanUp() {
            // 테스트에서 사용한 username으로 조회 후 삭제
            testDataCleaner.deleteUserCascade("wonderfulPark0206")
            testDataCleaner.deleteUserCascade("kimParent86")
            testDataCleaner.deleteUserCascade("kimParent86")
        }


        @Nested
        @DisplayName("회원가입")
        inner class SignUp {

            @Test
            @DisplayName("시터 회원가입이 정상적으로 이루어진다")
            fun signUpAsSitter() {
                // given
                val command = SignUpCommand(
                    username = "wonderfulPark0206",
                    rawPassword = "parak0206%^",
                    name = "박시터",
                    birthDate = LocalDate.of(1998, 2, 6),
                    gender = Gender.FEMALE,
                    email = "wonderfulPark0206@gmail.com",
                    roles = "SITTER",
                    sitterInfo = SitterProfileInfo.of(
                        minCareAge = 3,
                        maxCareAge = 5,
                        introduction = "유아교육과를 전공중인 대학생 시터입니다! 사촌 동생들을 많이 돌본 경험이 있어서 아이랑 잘 놀아줄 수 있어요."
                    ),
                    parentInfo = null
                )

                // when
                val result: SignUpResult = userService.signup(command)

                // then
                assertThat(result.username).isEqualTo("wonderfulPark0206")
                assertThat(result.role).isEqualTo("SITTER")
            }

            @Test
            @DisplayName("아이 정보 없이 부모 회원가입이 정상적으로 이루어진다")
            fun signUpAsParentOnly() {
                // given
                val command = SignUpCommand(
                    username = "kimParent86",
                    rawPassword = "86!@Kim",
                    name = "박부모",
                    birthDate = LocalDate.of(1986, 10, 19),
                    gender = Gender.FEMALE,
                    email = "kim86@gmail.com",
                    roles = "PARENT",
                    sitterInfo = null,
                    parentInfo = ParentProfileInfo.of(
                        children = emptyList(),
                    )
                )

                // when
                val result: SignUpResult = userService.signup(command)

                // then
                assertThat(result.username).isEqualTo("kimParent86")
                assertThat(result.role).isEqualTo("PARENT")
            }

            @Test
            @DisplayName("아이 정보와 함께 부모 회원가입이 정상적으로 이루어진다")
            fun signUpAsParentWithChildren() {
                // given
                val command = SignUpCommand(
                    username = "leeMom0322",
                    rawPassword = "momlee0322$",
                    name = "이부모",
                    birthDate = LocalDate.of(1990, 3, 22),
                    gender = Gender.FEMALE,
                    email = "leemom@gmail.com",
                    roles = "PARENT",
                    sitterInfo = null,
                    parentInfo = ParentProfileInfo.of(
                        children = listOf(
                            ChildInfo.of("민준", LocalDate.of(2020, 5, 1), Gender.MALE),
                            ChildInfo.of("지우", LocalDate.of(2022, 10, 10), Gender.FEMALE)
                        ),
                    )
                )

                // when
                val result: SignUpResult = userService.signup(command)

                // then
                assertThat(result.username).isEqualTo("leeMom0322")
                assertThat(result.role).isEqualTo("PARENT")
            }
        }
    }
