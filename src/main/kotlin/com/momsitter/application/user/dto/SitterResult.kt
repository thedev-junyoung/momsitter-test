package com.momsitter.application.user.dto

import com.momsitter.domain.sitter.SitterProfile

data class SitterResult(
    val careAgeRange: String,
    val introduction: String
) {
    companion object {
        fun from(sitterProfile: SitterProfile): SitterResult {
            return SitterResult(
                careAgeRange = "${sitterProfile.minCareAge}세 ~ ${sitterProfile.maxCareAge}세",
                introduction = sitterProfile.introduction
            )
        }
    }
}
