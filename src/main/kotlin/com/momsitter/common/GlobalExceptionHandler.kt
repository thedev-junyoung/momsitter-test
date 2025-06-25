package com.momsitter.common

import com.momsitter.domain.user.exceptions.InvalidLoginException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException::class)
    fun handleBusinessException(e: BusinessException): ResponseEntity<CustomApiResponse<Nothing>> {
        val errorMessage = e.message ?: "비즈니스 로직 오류가 발생했습니다."
        return ResponseEntity
            .status(e.errorCode.status)
            .body(CustomApiResponse.error(errorMessage))
    }

    @ExceptionHandler(InvalidLoginException::class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    fun handleInvalidLogin(e: InvalidLoginException): ResponseEntity<CustomApiResponse<Nothing>> {
        return ResponseEntity
            .status(HttpStatus.UNAUTHORIZED)
            .body(CustomApiResponse.error(e.message ?: "로그인 정보가 올바르지 않습니다."))
    }

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgument(ex: IllegalArgumentException): ResponseEntity<CustomApiResponse<Nothing>> {
        return ResponseEntity.badRequest()
            .body(CustomApiResponse.error(ex.message ?: "잘못된 요청입니다."))
    }

    @ExceptionHandler(Exception::class)
    fun handleGenericException(ex: Exception): ResponseEntity<CustomApiResponse<Nothing>> {
        ex.printStackTrace()
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(CustomApiResponse.error("서버 내부 오류가 발생했습니다."))
    }
}
