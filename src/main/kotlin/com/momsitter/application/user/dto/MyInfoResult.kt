package com.momsitter.application.user.dto

import com.momsitter.domain.user.User

data class MyInfoResult(
    val userId: Long,
    val name: String,
    val birthDate: String,
    val gender: String,
    val username: String,
    val password: String,
    val email: String,
    val sitterResult: SitterResult? = null,
    val parentResult: ParentResult? = null
){
    constructor(user: User) : this(
        userId = user.id,
        name = user.name,
        birthDate = user.birthDate.toString().replace("-", ""),
        gender = user.gender.name,
        username = user.username,
        password = user.password,
        email = user.email,
        sitterResult = user.sitterProfile?.let {
            SitterResult("${it.minCareAge}세 ~ ${it.maxCareAge}세", it.introduction)
        },
        parentResult = user.parentProfile?.let {
            ParentResult(
                children = it.children.map { child ->
                    ChildResult(child.name, child.birthDate.toString().replace("-", ""), child.gender.name)
                },
                request = it.careRequests.map { req ->
                    CareRequestResult.from(req)
                }
            )
        }
    )
}
