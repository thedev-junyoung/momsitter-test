package com.momsitter.presentation.child.dto.request


import com.momsitter.domain.user.Gender
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.AssertTrue
import java.time.LocalDate

data class UpdateChildRequest(
    @Schema(description = "이름", example = "민수")
    val name: String? = null,

    @Schema(description = "생년월일", example = "2019-05-12")
    val birthDate: LocalDate? = null,

    @Schema(description = "성별", example = "MALE")
    val gender: Gender? = null,
) {

    @AssertTrue(message = "이름이 빈 문자열일 수는 없습니다.")
    fun isNameValid(): Boolean {
        return name == null || name.isNotBlank()
    }
}
