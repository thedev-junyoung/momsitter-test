package com.momsitter.common

import org.springframework.http.HttpStatus

enum class ErrorCode(val status: HttpStatus, val message: String) {
    USER_NOT_FOUND(HttpStatus.BAD_REQUEST, "유저를 찾을 수 없습니다."),
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다."),
    DUPLICATE_ROLE(HttpStatus.BAD_REQUEST, "이미 존재하는 역할입니다."),
}
