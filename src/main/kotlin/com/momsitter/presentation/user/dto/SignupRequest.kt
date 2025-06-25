package com.momsitter.presentation.user.dto

import com.momsitter.domain.user.Gender
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.*
import java.time.LocalDate

@Schema(description = "회원가입 요청")
data class SignupRequest(
    @Schema(description = "아이디", example = "wonderfulPark0206")
    @field:NotBlank
    val username: String,

    @Schema(description = "비밀번호", example = "parak0206%^")
    @field:NotBlank
    val password: String,

    @Schema(description = "이름", example = "박시터")
    @field:NotBlank
    val name: String,

    @Schema(description = "생년월일", example = "1998-02-06")
    @field:NotNull
    val birthDate: LocalDate,

    @Schema(description = "성별", example = "FEMALE")
    @field:NotBlank
    val gender: String,

    @Schema(description = "이메일", example = "wonderfulPark0206@gmail.com")
    @field:Email
    val email: String,

    @Schema(description = "역할", example = "SITTER, PARENT")
    @field:NotBlank
    val roles: String,

    @Schema(description = "시터 정보", nullable = true)
    val sitter: SitterInfoRequest? = null,

    @Schema(description = "부모 정보", nullable = true)
    val parent: ParentInfoRequest? = null,
)

data class SitterInfoRequest(
    @Schema(description = "케어 가능 최소 연령", example = "3")
    @field:Min(0)
    val minAge: Int,

    @Schema(description = "케어 가능 최대 연령", example = "5")
    @field:Min(0)
    val maxAge: Int,

    @Schema(description = "자기소개", example = "아이를 좋아하는 교육학과 학생입니다.")
    val introduction: String
)

data class ParentInfoRequest(
    @Schema(description = "아이 정보 목록")
    val children: List<ChildRequest>
)

data class ChildRequest(

    @Schema(description = "아이 이름", example = "박아기")
    @field:NotBlank
    val name: String,

    @Schema(description = "생년월일", example = "2020-01-01")
    val birthDate: LocalDate,

    @Schema(description = "성별", example = "MALE")
    val gender: Gender
)
