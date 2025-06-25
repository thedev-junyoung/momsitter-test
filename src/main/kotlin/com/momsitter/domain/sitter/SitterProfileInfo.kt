package com.momsitter.domain.sitter

data class SitterProfileInfo(
    val minCareAge: Int,
    val maxCareAge: Int,
    val introduction: String
){
    companion object {
        fun from(sitter: SitterProfileInfo): SitterProfileInfo {
            return SitterProfileInfo(
                minCareAge = sitter.minCareAge,
                maxCareAge = sitter.maxCareAge,
                introduction = sitter.introduction
            )
        }
        fun of(
            minCareAge: Int,
            maxCareAge: Int,
            introduction: String
        ): SitterProfileInfo {
            return SitterProfileInfo(
                minCareAge = minCareAge,
                maxCareAge = maxCareAge,
                introduction = introduction
            )
        }
    }
}
