    package com.momsitter.application.user

    import com.momsitter.application.user.service.UserService
    import com.momsitter.application.user.dto.command.SignUpCommand
    import com.momsitter.application.user.dto.result.SignUpResult
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
            listOf(
                "wonderfulPark0206",
                "kimParent86",
                "leeMom0322",
                "wonderfulSitter",
                "parentKim88"
            ).forEach { testDataCleaner.deleteUserCascade(it) }
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

        @Nested
        @DisplayName("내 정보 조회")
        inner class GetMyInfo {

            @Test
            @DisplayName("시터 역할의 유저는 시터 정보가 포함된 MyInfoResult가 반환된다")
            fun getMyInfoForSitter() {
                // given
                val signUpResult = userService.signup(
                    SignUpCommand(
                        username = "wonderfulSitter",
                        rawPassword = "sitter123!",
                        name = "시터김",
                        birthDate = LocalDate.of(1995, 8, 15),
                        gender = Gender.FEMALE,
                        email = "sitter@example.com",
                        roles = "SITTER",
                        sitterInfo = SitterProfileInfo.of(3, 6, "아이들을 좋아하는 시터입니다."),
                        parentInfo = null
                    )
                )

                // when
                val myInfo = userService.getMyInfo(signUpResult.userId)

                // then
                assertThat(myInfo.userId).isEqualTo(signUpResult.userId)
                assertThat(myInfo.sitterResult).isNotNull
                assertThat(myInfo.sitterResult?.careAgeRange).isEqualTo("3세 ~ 6세")
                assertThat(myInfo.parentResult).isNull()
            }

            @Test
            @DisplayName("부모 역할의 유저는 자녀 정보와 신청 정보가 포함된 MyInfoResult가 반환된다")
            fun getMyInfoForParent() {
                // given
                val signUpResult = userService.signup(
                    SignUpCommand(
                        username = "parentKim88",
                        rawPassword = "parentKim!!",
                        name = "김부모",
                        birthDate = LocalDate.of(1988, 9, 9),
                        gender = Gender.MALE,
                        email = "parent@example.com",
                        roles = "PARENT",
                        sitterInfo = null,
                        parentInfo = ParentProfileInfo.of(
                            children = listOf(
                                ChildInfo.of("민서", LocalDate.of(2019, 1, 20), Gender.FEMALE),
                                ChildInfo.of("지후", LocalDate.of(2021, 6, 14), Gender.MALE)
                            )
                        )
                    )
                )

                // when
                val myInfo = userService.getMyInfo(signUpResult.userId)

                // then
                assertThat(myInfo.userId).isEqualTo(signUpResult.userId)
                assertThat(myInfo.parentResult).isNotNull
                assertThat(myInfo.parentResult?.children?.size).isEqualTo(2)
                assertThat(myInfo.sitterResult).isNull()
            }
        }
    }
