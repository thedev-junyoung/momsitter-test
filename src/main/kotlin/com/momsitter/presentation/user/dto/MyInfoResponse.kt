package com.momsitter.presentation.user.dto

import com.momsitter.application.user.dto.result.*
import com.momsitter.domain.care.CareRequestStatus
import java.time.LocalDate
import java.time.LocalDateTime

data class MyInfoResponse(
    val userId: Long,
    val name: String,
    val birthDate: String,
    val gender: String,
    val username: String,
    val email: String,
    val sitter: SitterResponse? = null,
    val parent: ParentResponse? = null
) {
    companion object {
        fun from(result: MyInfoResult): MyInfoResponse {
            return MyInfoResponse(
                userId = result.userId,
                name = result.name,
                birthDate = result.birthDate,
                gender = result.gender,
                username = result.username,
                email = result.email,
                sitter = result.sitterResult?.let { SitterResponse(it) },
                parent = result.parentResult?.let { ParentResponse(it) }
            )
        }
    }
}

data class SitterResponse(val careAgeRange: String, val introduction: String) {
    constructor(result: SitterResult) : this(
        careAgeRange = result.careAgeRange,
        introduction = result.introduction
    )
}

data class ParentResponse(
    val children: List<ChildResponse>,
    val request: List<CareRequestResponse>
) {
    constructor(result: ParentResult) : this(
        children = result.children.map { ChildResponse(it) },
        request = result.request.map { CareRequestResponse(it) }
    )
}

data class ChildResponse(val name: String, val birthDate: LocalDate, val gender: String) {
    constructor(result: ChildResult) : this(
        name = result.name,
        birthDate = result.birthDate,
        gender = result.gender
    )
}

data class CareRequestResponse(
    val id: Long,
    val content: String,
    val status: CareRequestStatus,
    val createdAt: LocalDateTime?
) {
    constructor(result: CareRequestResult) : this(
        id = result.id,
        content = result.content,
        status = result.status,
        createdAt = result.createdAt
    )
}
