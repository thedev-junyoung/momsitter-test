package com.momsitter.presentation.child.controller

import com.momsitter.common.CustomApiResponse
import com.momsitter.presentation.child.dto.request.CreateChildRequest
import com.momsitter.presentation.child.dto.request.UpdateChildRequest
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.servlet.http.HttpServletRequest
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestBody

@Tag(name = "Child", description = "자녀 정보 관련 API")
interface ChildAPI {

    @Operation(summary = "자녀 등록", description = "새로운 자녀 정보를 등록합니다.")
    fun createChild(
        request: HttpServletRequest,
        @Valid @RequestBody requestDto: CreateChildRequest
    ): ResponseEntity<CustomApiResponse<Long>>


    @Operation(
        summary = "자녀 정보 수정",
        description = "자녀의 이름, 생년월일, 성별을 수정합니다."
    )
    fun updateChild(
        request: HttpServletRequest,
        childId: Long,
        @Valid @RequestBody requestDto: UpdateChildRequest
    ): ResponseEntity<CustomApiResponse<Unit>>
}