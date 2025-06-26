package com.momsitter.presentation.user.dto.request

import com.momsitter.domain.user.Gender
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.Valid
import jakarta.validation.constraints.*
import java.time.LocalDate

@Schema(description = "회원가입 요청 DTO")
data class SignupRequest(

    @field:NotBlank(message = "아이디는 필수입니다.")
    @Schema(description = "아이디", example = "wonderfulPark0206")
    val username: String,

    @field:NotBlank(message = "비밀번호는 필수입니다.")
    @Schema(description = "비밀번호", example = "parak0206%^")
    val password: String,

    @field:NotBlank(message = "이름은 필수입니다.")
    @Schema(description = "이름", example = "박시터")
    val name: String,

    @field:NotNull(message = "생년월일은 필수입니다.")
    @Schema(description = "생년월일", example = "1998-02-06", type = "string", format = "date")
    val birthDate: LocalDate,

    @field:NotBlank(message = "성별은 필수입니다.")
    @Schema(description = "성별", example = "FEMALE", allowableValues = ["MALE", "FEMALE"])
    val gender: String,

    @field:Email(message = "이메일 형식이 올바르지 않습니다.")
    @Schema(description = "이메일", example = "wonderfulPark0206@gmail.com")
    val email: String,

    @field:NotBlank(message = "역할은 필수입니다.")
    @Schema(description = "역할 (콤마로 구분된 문자열)", example = "SITTER,PARENT", allowableValues = ["SITTER", "PARENT"])
    val roles: String,

    @field:Valid
    @Schema(description = "시터 정보", nullable = true)
    val sitter: SitterInfoRequest? = null,

    @field:Valid
    @Schema(description = "부모 정보", nullable = true)
    val parent: ParentInfoRequest? = null,
)


