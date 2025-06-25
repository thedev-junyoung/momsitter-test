package com.momsitter.domain.user

import com.momsitter.domain.sitter.SitterProfileInfo
import java.time.LocalDate

object TestUserFactory {

    fun parentOnlyUser(): User {
        val user = User.signUpAsParentOnly(
            username = "kimParent86",
            password = "encodedPassword",
            name = "박부모",
            birthDate = LocalDate.of(1986, 10, 19),
            gender = Gender.FEMALE,
            email = "kim86@gmail.com",
            activeRole = UserRoleType.PARENT,
            role = UserRoleType.PARENT
        )
        return user
    }

    fun sitterUser(): User {
        val user = User.signUpAsSitter(
            username = "wonderfulPark0206",
            password = "encodedPassword",
            name = "박시터",
            birthDate = LocalDate.of(1998, 2, 6),
            gender = Gender.FEMALE,
            email = "wonderfulPark0206@gmail.com",
            role = UserRoleType.SITTER,
            activeRole = UserRoleType.SITTER,
            sitterInfo = SitterProfileInfo(3, 5, "유아교육과를 전공중인 대학생 시터입니다!")
        )
        return user
    }
}
