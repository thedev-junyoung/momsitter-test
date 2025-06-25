package com.momsitter.common

class BusinessException(
    override val message: String,
    val errorCode: ErrorCode
) : RuntimeException(message)
