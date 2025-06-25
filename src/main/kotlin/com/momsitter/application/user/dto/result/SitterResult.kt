package com.momsitter.application.user.dto.result

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

        fun of(
            minCareAge: Int,
            maxCareAge: Int,
            introduction: String
        ): SitterResult {
            return SitterResult(
                careAgeRange = "${minCareAge}세 ~ ${maxCareAge}세",
                introduction = introduction
            )
        }
    }
}
