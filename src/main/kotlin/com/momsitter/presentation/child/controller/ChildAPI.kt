package com.momsitter.presentation.child.controller

import com.momsitter.common.CustomApiResponse
import com.momsitter.presentation.child.dto.request.UpdateChildRequest
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.ResponseEntity

@Tag(name = "Child", description = "자녀 정보 관련 API")
interface ChildAPI {

    @Operation(
        summary = "자녀 정보 수정",
        description = "자녀의 이름, 생년월일, 성별을 수정합니다."
    )
    fun updateChild(
        request: HttpServletRequest,
        childId: Long,
        requestDto: UpdateChildRequest
    ): ResponseEntity<CustomApiResponse<Unit>>
}