package com.momsitter.domain.parent

import com.momsitter.domain.child.ChildInfo

data class ParentProfileInfo(
    val children: List<ChildInfo>
) {
    companion object {
        fun from(parentProfile: ParentProfile): ParentProfileInfo {
            return ParentProfileInfo(
                children = parentProfile.children
                    ?.map { ChildInfo.from(it) }
                    ?: emptyList()
            )
        }

        fun of(children: List<ChildInfo>): ParentProfileInfo {
            return ParentProfileInfo(children)
        }
    }
}
