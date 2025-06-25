package com.momsitter.application.user.dto.result

import com.momsitter.domain.child.Child
import com.momsitter.domain.user.User
import java.time.LocalDate

data class ExtendToParentResult(
    val userId: Long,
    val roles: List<String>,
    val parentProfile: ParentProfileResult?
) {
    companion object {
        fun from(user: User): ExtendToParentResult {
            return ExtendToParentResult(
                userId = user.id,
                roles = user.roles.map { it.name },
                parentProfile = user.parentProfile?.let {
                    ParentProfileResult.from(it.children)
                }
            )
        }
    }

    data class ParentProfileResult(
        val children: List<ChildResult>
    ) {
        companion object {
            fun from(children: List<Child>): ParentProfileResult {
                return ParentProfileResult(
                    children = children.map { ChildResult.from(it) }
                )
            }
        }
    }

    data class ChildResult(
        val name: String,
        val birthDate: LocalDate,
        val gender: String
    ) {
        companion object {
            fun from(child: Child): ChildResult {
                return ChildResult(
                    name = child.name,
                    birthDate = child.birthDate,
                    gender = child.gender.name
                )
            }
        }
    }
}
