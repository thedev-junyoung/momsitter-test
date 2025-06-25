package com.momsitter.application.user.dto.result

import com.momsitter.domain.care.CareRequest
import com.momsitter.domain.care.CareRequestStatus
import java.time.LocalDateTime

data class CareRequestResult(
    val id: Long,
    val content: String,
    val status: CareRequestStatus,
    val createdAt: LocalDateTime?
) {
    companion object {
        fun from(careRequest: CareRequest): CareRequestResult {
            return CareRequestResult(
                id = careRequest.id,
                content = careRequest.content,
                status = careRequest.status,
                createdAt = careRequest.createdAt
            )
        }
    }
}
