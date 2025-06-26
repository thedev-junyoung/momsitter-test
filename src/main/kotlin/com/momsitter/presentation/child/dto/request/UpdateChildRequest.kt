package com.momsitter.presentation.child.dto.request

import com.momsitter.domain.user.Gender
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDate

data class UpdateChildRequest(

    @Schema(
        description = "자녀 이름",
        example = "민수",
        nullable = true,
        required = false
    )
    val name: String? = null,

    @Schema(
        description = "자녀 생년월일 (yyyy-MM-dd)",
        example = "2019-05-12",
        nullable = true,
        required = false
    )
    val birthDate: LocalDate? = null,

    @Schema(
        description = "자녀 성별 (MALE 또는 FEMALE)",
        example = "MALE",
        nullable = true,
        required = false
    )
    val gender: Gender? = null,
)
