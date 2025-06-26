package com.momsitter.presentation.sitter.controller

import com.momsitter.application.sitter.dto.UpdateSitterProfileCommand
import com.momsitter.application.sitter.sevice.SitterProfileService
import com.momsitter.common.CustomApiResponse
import com.momsitter.presentation.sitter.dto.request.UpdateSitterProfileRequest
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/sitter")
class SitterProfileController(
    private val sitterProfileService: SitterProfileService
) : SitterProfileAPI {

    @PatchMapping("/profile")
    override fun updateSitterProfile(
        request: HttpServletRequest,
        @RequestBody requestDto: UpdateSitterProfileRequest
    ): ResponseEntity<CustomApiResponse<Unit>> {
        val userId = request.getAttribute("userId") as Long
        val command = UpdateSitterProfileCommand(
            userId = userId,
            minCareAge = requestDto.minCareAge,
            maxCareAge = requestDto.maxCareAge,
            introduction = requestDto.introduction
        )
        sitterProfileService.updateSitterProfile(command)
        return ResponseEntity.ok(CustomApiResponse.success(Unit, "시터 프로필이 수정되었습니다."))
    }

}
