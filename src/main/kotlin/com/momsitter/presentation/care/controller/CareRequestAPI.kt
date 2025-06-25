package com.momsitter.presentation.care.controller

import com.momsitter.common.CustomApiResponse
import com.momsitter.presentation.care.dto.CreateCareRequestRequest
import com.momsitter.presentation.care.dto.UpdateCareRequestRequest
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.servlet.http.HttpServletRequest
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity

@Tag(name = "Care Request API", description = "Care Request 관련 API")
interface CareRequestAPI {

    @Operation(summary = "Create Care Request", description = "돌봄 요청을 생성합니다.")
    fun create(request: HttpServletRequest, @Valid command: CreateCareRequestRequest): ResponseEntity<CustomApiResponse<Long>>

    @Operation(summary = "Update Care Request", description = "돌봄 요청을 수정합니다.")
    fun update(request: HttpServletRequest, id: Long, @Valid command: UpdateCareRequestRequest): ResponseEntity<CustomApiResponse<Unit>>
}
