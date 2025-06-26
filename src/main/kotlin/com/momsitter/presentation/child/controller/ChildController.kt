package com.momsitter.presentation.child.controller

import com.momsitter.application.child.dto.UpdateChildCommand
import com.momsitter.application.child.sevice.ChildService
import com.momsitter.common.CustomApiResponse
import com.momsitter.presentation.child.dto.request.UpdateChildRequest
import jakarta.servlet.http.HttpServletRequest
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/child")
class ChildController (
    private val childService: ChildService
): ChildAPI{

    @PatchMapping("/{childId}/profile")
    override fun updateChild(
        request: HttpServletRequest,
        @PathVariable childId: Long,
        @Valid @RequestBody requestDto: UpdateChildRequest
    ): ResponseEntity<CustomApiResponse<Unit>> {
        val userId = request.getAttribute("userId") as Long
        childService.updateChild(
            UpdateChildCommand.of(userId = userId, childId = childId,requestDto = requestDto)
        )
        return ResponseEntity.ok(CustomApiResponse.success(Unit, "자녀 정보가 수정되었습니다."))
    }




}