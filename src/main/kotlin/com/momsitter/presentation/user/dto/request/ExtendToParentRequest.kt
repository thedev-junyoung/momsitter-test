package com.momsitter.presentation.user.dto.request

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.Valid

@Schema(description = "시터 → 부모 역할 확장 요청")
data class ExtendToParentRequest(

    @field:Valid
    @Schema(
        description = "자녀 정보 목록. 없을 수도 있음",
        nullable = true,
        example = """
[
  {
    "name": "지우",
    "birthDate": "2020-03-15",
    "gender": "FEMALE"
  }
]
"""
    )
    val children: List<ChildRequest>? = null
)
