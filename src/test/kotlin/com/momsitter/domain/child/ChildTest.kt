package com.momsitter.domain.child

import com.momsitter.domain.parent.ParentProfile
import com.momsitter.domain.user.Gender
import com.momsitter.domain.user.User
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.time.LocalDate


@DisplayName("아이 도메인 단위 테스트")
class ChildTest {

    private fun createDummyParentProfile(): ParentProfile {
        return ParentProfile.of(User.dummy())
    }

    private fun createChild(): Child {
        val parentProfile = createDummyParentProfile()
        return Child.of(parentProfile, "기존이름", LocalDate.of(2020, 1, 1), Gender.FEMALE)
    }

    @Test
    @DisplayName("아이의 이름, 생일, 성별을 모두 수정할 수 있다")
    fun update_all_fields_successfully() {
        val child = createChild()

        child.update(
            name = "수정된이름",
            birthDate = LocalDate.of(2021, 5, 10),
            gender = Gender.MALE
        )

        assertEquals("수정된이름", child.name)
        assertEquals(LocalDate.of(2021, 5, 10), child.birthDate)
        assertEquals(Gender.MALE, child.gender)
    }

    @Test
    @DisplayName("아이 이름만 수정할 수 있다")
    fun update_name_only() {
        val child = createChild()

        child.update(name = "새이름", birthDate = null, gender = null)

        assertEquals("새이름", child.name)
        assertEquals(LocalDate.of(2020, 1, 1), child.birthDate)
        assertEquals(Gender.FEMALE, child.gender)
    }

    @Test
    @DisplayName("생일만 수정할 수 있다")
    fun update_birth_date_only() {
        val child = createChild()

        child.update(name = null, birthDate = LocalDate.of(2022, 3, 15), gender = null)

        assertEquals(LocalDate.of(2022, 3, 15), child.birthDate)
    }

    @Test
    @DisplayName("성별만 수정할 수 있다")
    fun update_gender_only() {
        val child = createChild()

        child.update(name = null, birthDate = null, gender = Gender.MALE)

        assertEquals(Gender.MALE, child.gender)
    }

    @Test
    @DisplayName("빈 문자열 이름으로 수정 시 예외가 발생한다")
    fun throw_when_name_is_blank() {
        val child = createChild()

        val exception = assertThrows(IllegalArgumentException::class.java) {
            child.update(name = "   ", birthDate = null, gender = null)
        }

        assertEquals("아이 이름은 비어 있을 수 없습니다.", exception.message)
    }

    @Test
    @DisplayName("모든 인자가 null인 경우 아무 것도 변경되지 않는다")
    fun update_with_all_null_should_keep_original_values() {
        val child = createChild()
        val originalName = child.name
        val originalBirthDate = child.birthDate
        val originalGender = child.gender

        child.update(name = null, birthDate = null, gender = null)

        assertEquals(originalName, child.name)
        assertEquals(originalBirthDate, child.birthDate)
        assertEquals(originalGender, child.gender)
    }
}
