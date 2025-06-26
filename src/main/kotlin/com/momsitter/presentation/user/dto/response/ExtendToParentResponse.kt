package com.momsitter.presentation.user.dto.response

import com.momsitter.application.user.dto.result.ExtendToParentResult
import com.momsitter.domain.user.UserRoleType
import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "부모 역할 확장 응답")
data class ExtendToParentResponse(
    @Schema(description = "유저 ID")
    val userId: Long,

    @Schema(description = "현재 보유 역할 목록")
    val roles: Set<UserRoleType>,

    @Schema(description = "자녀 목록")
    val children: List<ChildResponse>
) {
    companion object {
        fun from(result: ExtendToParentResult): ExtendToParentResponse {
            return ExtendToParentResponse(
                userId = result.userId,
                roles = result.roles,
                children = result.parentProfile?.children?.map {
                    ChildResponse(it.name, it.birthDate, it.gender)
                } ?: emptyList()
            )
        }
    }
}
