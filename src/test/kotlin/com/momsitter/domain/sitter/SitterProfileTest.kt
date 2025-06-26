package com.momsitter.domain.sitter

import com.momsitter.domain.user.User
import org.junit.jupiter.api.*

@DisplayName("시터 프로필 도메인 단위 테스트")
class SitterProfileTest {

    private fun createDummyUser(): User = User.dummy()

    private fun createSitterProfile(): SitterProfile {
        return SitterProfile.of(createDummyUser(), 3, 10, "기존 소개입니다.")
    }

    @Test
    @DisplayName("전체 시터 정보를 수정할 수 있다")
    fun update_all_fields_successfully() {
        val profile = createSitterProfile()

        profile.update(minCareAge = 5, maxCareAge = 12, introduction = "새로운 소개입니다.")

        Assertions.assertEquals(5, profile.minCareAge)
        Assertions.assertEquals(12, profile.maxCareAge)
        Assertions.assertEquals("새로운 소개입니다.", profile.introduction)
    }

    @Test
    @DisplayName("최소 케어 연령만 수정할 수 있다")
    fun update_min_care_age_only() {
        val profile = createSitterProfile()

        profile.update(minCareAge = 6, maxCareAge = null, introduction = null)

        Assertions.assertEquals(6, profile.minCareAge)
        Assertions.assertEquals(10, profile.maxCareAge)
    }

    @Test
    @DisplayName("최대 케어 연령만 수정할 수 있다")
    fun update_max_care_age_only() {
        val profile = createSitterProfile()

        profile.update(minCareAge = null, maxCareAge = 15, introduction = null)

        Assertions.assertEquals(3, profile.minCareAge)
        Assertions.assertEquals(15, profile.maxCareAge)
    }

    @Test
    @DisplayName("자기소개만 수정할 수 있다")
    fun update_introduction_only() {
        val profile = createSitterProfile()

        profile.update(minCareAge = null, maxCareAge = null, introduction = "업데이트 소개입니다.")

        Assertions.assertEquals("업데이트 소개입니다.", profile.introduction)
    }

    @Test
    @DisplayName("최소 케어 연령이 0이면 예외가 발생한다")
    fun throw_when_min_care_age_is_zero() {
        val profile = createSitterProfile()

        val ex = assertThrows<IllegalArgumentException> {
            profile.update(minCareAge = 0, maxCareAge = null, introduction = null)
        }

        Assertions.assertEquals("케어 연령은 0보다 커야 합니다.", ex.message)
    }

    @Test
    @DisplayName("최소 연령이 최대 연령보다 크면 예외가 발생한다")
    fun throw_when_min_care_age_is_greater_than_max() {
        val profile = createSitterProfile()

        val ex = assertThrows<IllegalArgumentException> {
            profile.update(minCareAge = 11, maxCareAge = 10, introduction = null)
        }

        Assertions.assertEquals("최소 나이는 최대 나이보다 작아야 합니다.", ex.message)
    }

    @Test
    @DisplayName("기존 최소 연령보다 최대 연령이 작으면 예외가 발생한다")
    fun throw_when_max_care_age_is_less_than_existing_min() {
        val profile = createSitterProfile()

        val ex = assertThrows<IllegalArgumentException> {
            profile.update(minCareAge = null, maxCareAge = 2, introduction = null)
        }

        Assertions.assertEquals("최소 나이는 최대 나이보다 작아야 합니다.", ex.message)
    }
}
