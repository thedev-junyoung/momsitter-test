package com.momsitter.presentation.care.controller

import com.momsitter.application.care.CareRequestService
import com.momsitter.common.CustomApiResponse
import com.momsitter.presentation.care.dto.CreateCareRequestRequest
import com.momsitter.presentation.care.dto.UpdateCareRequestRequest
import jakarta.servlet.http.HttpServletRequest
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/care-requests")
class CareRequestController(
    private val careRequestService: CareRequestService
): CareRequestAPI{

    @PostMapping
    override fun create(
        request: HttpServletRequest,
        @RequestBody @Valid command: CreateCareRequestRequest
    ): ResponseEntity<CustomApiResponse<Long>> {
        val userId = request.getAttribute("userId") as Long
        val careRequestId = careRequestService.createCareRequest(command.toCommand(userId))
        return ResponseEntity.ok(CustomApiResponse.success(careRequestId, "돌봄 요청 생성 성공"))
    }

    @PutMapping("/{id}")
    override fun update(
        request: HttpServletRequest,
        @PathVariable id: Long,
        @RequestBody @Valid command: UpdateCareRequestRequest
    ): ResponseEntity<CustomApiResponse<Unit>> {
        val userId = request.getAttribute("userId") as Long
        careRequestService.updateCareRequest(command.toCommand(id, userId))
        return ResponseEntity.ok(CustomApiResponse.success(Unit, "돌봄 요청 수정 성공"))
    }
}
