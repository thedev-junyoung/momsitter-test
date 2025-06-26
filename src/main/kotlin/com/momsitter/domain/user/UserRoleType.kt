package com.momsitter.domain.user

import com.fasterxml.jackson.annotation.JsonCreator
import com.momsitter.common.BusinessException
import com.momsitter.common.ErrorCode

enum class UserRoleType {
    SITTER, PARENT, DEFAULT;
    companion object {
        @JsonCreator
        @JvmStatic
        fun from(value: String): UserRoleType {
            return entries.find { it.name.equals(value, ignoreCase = true) }
                ?: throw BusinessException(
                    "지원하지 않는 사용자 역할입니다. $value",
                    ErrorCode.INVALID_USER_ROLE
                )
        }
    }
}
