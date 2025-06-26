package com.momsitter.application.care

import com.momsitter.application.care.dto.CreateCareRequestCommand
import com.momsitter.application.care.dto.UpdateCareRequestCommand
import com.momsitter.domain.care.CareRequestRepository
import com.momsitter.domain.user.Gender
import com.momsitter.domain.user.TestUserFactory
import com.momsitter.domain.user.User
import com.momsitter.domain.user.UserRoleType
import com.momsitter.domain.user.UserRepository
import com.momsitter.support.TestDataCleaner
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDate

@SpringBootTest
class CareRequestServiceIntegrationTest {

    @Autowired
    lateinit var careRequestService: CareRequestService

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var careRequestRepository: CareRequestRepository

    @Autowired
    lateinit var testDataCleaner: TestDataCleaner

    @AfterEach
    fun tearDown() {
        testDataCleaner.deleteUserCascade("parentUser")
    }

    @Test
    @DisplayName("돌봄 요청을 생성할 수 있다")
    fun should_createCareRequest() {
        // given
        val parent = TestUserFactory.createParentOnlyUser(
            username = "parentUser",
            password = "pass1234!",
            name = "엄마",
            birthDate = LocalDate.of(1985, 5, 5),
            gender = Gender.FEMALE,
            email = "parent@example.com",
        )
        parent.parentProfile!!.addChild("아이1", LocalDate.of(2020, 1, 1), Gender.MALE)
        val savedUser = userRepository.save(parent)

        // when
        val requestId = careRequestService.createCareRequest(
            CreateCareRequestCommand(savedUser.id, "돌봐주세요")
        )

        // then
        val savedRequest = careRequestRepository.findWithParentAndUserById(requestId)
            .orElseThrow { AssertionError("돌봄 요청이 저장되지 않았습니다.") }
        assertThat(savedRequest.content).isEqualTo("돌봐주세요")
        assertThat(savedRequest.parentProfile?.user?.id).isEqualTo(savedUser.id)
    }

    @Test
    @DisplayName("돌봄 요청을 수정할 수 있다")
    fun should_updateCareRequest() {
        // given
        val parent = TestUserFactory.createParentOnlyUser(
            username = "parentUser",
            password = "pass1234!",
            name = "엄마",
            birthDate = LocalDate.of(1985, 5, 5),
            gender = Gender.FEMALE,
            email = "parent@example.com",
        )
        val profile = parent.parentProfile!!
        profile.addChild("아이1", LocalDate.of(2020, 1, 1), Gender.FEMALE)
        val savedUser = userRepository.save(parent)
        val requestId = careRequestService.createCareRequest(CreateCareRequestCommand(savedUser.id, "초기내용"))

        // when
        careRequestService.updateCareRequest(UpdateCareRequestCommand(requestId, savedUser.id, "수정된 내용"))

        // then
        val updatedRequest = careRequestRepository.findById(requestId).get()
        assertThat(updatedRequest.content).isEqualTo("수정된 내용")
    }
}
