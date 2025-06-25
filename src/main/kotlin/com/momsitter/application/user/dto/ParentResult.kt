package com.momsitter.application.user.dto

import com.momsitter.domain.parent.ParentProfile

data class ParentResult(
    val children: List<ChildResult>,
    val request: List<CareRequestResult>
) {
    companion object {
        fun from(parentProfile: ParentProfile): ParentResult {
            return ParentResult(
                children = parentProfile.children.map { ChildResult.from(it) },
                request = parentProfile.careRequests.map { CareRequestResult.from(it) }
            )
        }
    }
}
