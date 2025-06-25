package com.momsitter.application.user.dto.command

import com.momsitter.domain.child.ChildInfo

data class ExtendToParentCommand(
    val userId: Long,
    val children: List<ChildInfo>? = null
)
