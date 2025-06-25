package com.momsitter.presentation.user.dto.response

import com.momsitter.application.user.dto.result.ExtendToSitterResult
import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "시터 역할 확장 응답")
data class ExtendToSitterResponse(
    @Schema(description = "유저 ID")
    val userId: Long,

    @Schema(description = "현재 보유 역할 목록")
    val roles: List<String>,

    @Schema(description = "시터 프로필")
    val sitterProfile: SitterResponse?
) {
    companion object {
        fun from(result: ExtendToSitterResult): ExtendToSitterResponse {
            return ExtendToSitterResponse(
                userId = result.userId,
                roles = result.roles,
                sitterProfile = result.sitterProfile?.let {
                    SitterResponse(it.careAgeRange, it.introduction)
                }
            )
        }
    }
}
