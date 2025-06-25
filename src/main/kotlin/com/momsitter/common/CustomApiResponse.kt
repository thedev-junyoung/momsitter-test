package com.momsitter.common

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "공통 API 응답 포맷")
data class CustomApiResponse<T>(
    @Schema(description = "응답 상태", example = "SUCCESS")
    val status: String,

    @Schema(description = "응답 메시지", example = "요청이 성공적으로 처리되었습니다.")
    val message: String,

    @Schema(description = "응답 데이터", nullable = true)
    val data: T? = null
) {
    companion object {
        fun <T> success(data: T?, message: String = "요청이 성공적으로 처리되었습니다."): CustomApiResponse<T> {
            return CustomApiResponse(
                status = "SUCCESS",
                message = message,
                data = data
            )
        }

        fun error(message: String): CustomApiResponse<Nothing> {
            return CustomApiResponse(
                status = "ERROR",
                message = message,
                data = null
            )
        }
    }
}
