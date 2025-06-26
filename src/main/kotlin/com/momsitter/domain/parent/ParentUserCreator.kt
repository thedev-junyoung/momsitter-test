package com.momsitter.domain.parent

import com.momsitter.domain.child.ChildInfo
import com.momsitter.domain.user.*
import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
class ParentUserCreator {

    fun createWithoutChildren(
        username: String,
        password: String,
        name: String,
        birthDate: LocalDate,
        gender: Gender,
        email: String,
    ): User {
        val user = User.basicUser(
            username = username,
            password = password,
            name = name,
            birthDate = birthDate,
            gender = gender,
            email = email
        )
        val parentProfile = ParentProfile.of(user)
        user.assignParentProfile(parentProfile)

        return user
    }

    fun createWithChildren(
        username: String,
        password: String,
        name: String,
        birthDate: LocalDate,
        gender: Gender,
        email: String,
        children: List<ChildInfo>
    ): User {
        val user = createWithoutChildren(
            username, password, name, birthDate, gender, email
        )

        val profile = user.parentProfile ?: throw IllegalStateException("부모 프로필이 없습니다")
        children.forEach { child ->
            profile.addChild(child.name, child.birthDate, child.gender)
        }
        return user
    }
}
