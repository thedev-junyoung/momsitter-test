package com.momsitter.presentation.user.dto.request

import com.momsitter.domain.user.Gender
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.time.LocalDate


data class ChildRequest(

    @field:NotBlank(message = "아이 이름은 필수입니다.")
    @Schema(description = "아이 이름", example = "박아기")
    val name: String,

    @field:NotNull(message = "생년월일은 필수입니다.")
    @Schema(description = "생년월일", example = "2020-01-01", type = "string", format = "date")
    val birthDate: LocalDate,

    @field:NotNull(message = "성별은 필수입니다.")
    @Schema(description = "성별", example = "MALE", allowableValues = ["MALE", "FEMALE"])
    val gender: Gender
)