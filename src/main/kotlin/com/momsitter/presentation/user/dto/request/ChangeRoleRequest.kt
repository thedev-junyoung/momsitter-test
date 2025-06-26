package com.momsitter.presentation.user.dto.request

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank

data class ChangeRoleRequest (
    @field:NotBlank
    @Schema(description = "역할", example = "SITTER", allowableValues = ["SITTER", "PARENT"])
    val newRole: String
){

}
