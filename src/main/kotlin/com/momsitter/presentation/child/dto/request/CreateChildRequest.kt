package com.momsitter.presentation.child.dto.request

import com.momsitter.domain.user.Gender
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.time.LocalDate

@Schema(description = "자녀 등록 요청")
data class CreateChildRequest(

    @field:NotBlank
    @Schema(description = "아이 이름", example = "박아기")
    val name: String,

    @field:NotNull
    @Schema(description = "생년월일", example = "2020-01-01", type = "string", format = "date")
    val birthDate: LocalDate,

    @field:NotNull
    @Schema(description = "성별", example = "MALE", allowableValues = ["MALE", "FEMALE"])
    val gender: Gender
)
