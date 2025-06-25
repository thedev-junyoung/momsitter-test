package com.momsitter.common

import org.springframework.http.HttpStatus

enum class ErrorCode(val status: HttpStatus, val message: String) {
    USER_NOT_FOUND(HttpStatus.BAD_REQUEST, "유저를 찾을 수 없습니다."),
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다."),
    DUPLICATE_ROLE(HttpStatus.BAD_REQUEST, "이미 존재하는 역할입니다."),
    PARENT_PROFILE_NOT_FOUND(HttpStatus.BAD_REQUEST, "부모 프로필을 찾을 수 없습니다."),
    CARE_REQUEST_NOT_FOUND(HttpStatus.BAD_REQUEST, "돌봄 요청을 찾을 수 없습니다."),
    CARE_REQUEST_FORBIDDEN(HttpStatus.FORBIDDEN, "접근이 금지되었습니다."),
    INVALID_NAME(HttpStatus.BAD_REQUEST, "유효하지 않은 이름입니다."),
    INVALID_EMAIL(HttpStatus.BAD_REQUEST, "유효하지 않은 이메일입니다."),
}
