package com.momsitter.presentation.sitter.controller

import com.momsitter.common.CustomApiResponse
import com.momsitter.presentation.sitter.dto.request.UpdateSitterProfileRequest
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.servlet.http.HttpServletRequest
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable

@Tag(name = "Sitter", description = "시터 프로필 관련 API")
interface SitterProfileAPI {

    @Operation(
        summary = "시터 프로필 수정",
        description = "시터 프로필을 수정합니다. 최소/최대 돌봄 연령과 자기소개를 업데이트합니다."
    )
    fun updateSitterProfile(
        request: HttpServletRequest,
        @Valid requestDto: UpdateSitterProfileRequest
    ): ResponseEntity<CustomApiResponse<Unit>>

}
