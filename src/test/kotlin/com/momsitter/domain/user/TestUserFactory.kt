package com.momsitter.domain.user

import com.momsitter.domain.child.ChildInfo
import com.momsitter.domain.parent.ParentProfile
import com.momsitter.domain.sitter.SitterProfile
import java.time.LocalDate

object TestUserFactory {

    fun parentOnlyUser(): User {
        val user = User.basicUser(
            username = "kimParent86",
            password = "encodedPassword",
            name = "박부모",
            birthDate = LocalDate.of(1986, 10, 19),
            gender = Gender.FEMALE,
            email = "kim86@gmail.com"
        )
        user.assignParentProfile(ParentProfile.of(user))
        user.changeActiveRole(UserRoleType.PARENT)
        return user
    }
    fun sitterUser(): User {
        val user = User.basicUser(
            username = "wonderfulPark0206",
            password = "encodedPassword",
            name = "박시터",
            birthDate = LocalDate.of(1998, 2, 6),
            gender = Gender.FEMALE,
            email = "wonderfulPark0206@gmail.com"
        )
        val sitterProfile = SitterProfile.of(
            user = user,
            minCareAge = 3,
            maxCareAge = 5,
            introduction = "유아교육과를 전공중인 대학생 시터입니다!"
        )
        user.assignSitterProfile(sitterProfile)
        user.changeActiveRole(UserRoleType.SITTER)
        return user
    }



    fun createSitterUser(
        username: String = "defaultSitter",
        password: String = "defaultPassword",
        name: String = "시터이름",
        birthDate: LocalDate = LocalDate.of(1995, 5, 5),
        gender: Gender = Gender.FEMALE,
        email: String = "sitter@test.com",
        minCareAge: Int = 1,
        maxCareAge: Int = 5,
        introduction: String = "소개 문구"
    ): User {
        val user = User.basicUser(username, password, name, birthDate, gender, email)
        val sitterProfile = SitterProfile.of(user, minCareAge, maxCareAge, introduction)
        user.assignSitterProfile(sitterProfile)
        user.changeActiveRole(UserRoleType.SITTER)
        return user
    }

    fun createParentOnlyUser(
        username: String = "defaultParent",
        password: String = "defaultPassword",
        name: String = "부모이름",
        birthDate: LocalDate = LocalDate.of(1980, 1, 1),
        gender: Gender = Gender.FEMALE,
        email: String = "parent@test.com"
    ): User {
        val user = User.basicUser(username, password, name, birthDate, gender, email)
        user.assignParentProfile(ParentProfile.of(user))
        user.changeActiveRole(UserRoleType.PARENT)
        return user
    }


    fun createParentWithChildrenUser(
        username: String = "defaultParentWithChild",
        password: String = "defaultPassword",
        name: String = "부모이름",
        birthDate: LocalDate = LocalDate.of(1985, 3, 15),
        gender: Gender = Gender.MALE,
        email: String = "parentwithchild@test.com",
        children: List<ChildInfo>
    ): User {
        val user = User.basicUser(username, password, name, birthDate, gender, email)
        val parentProfile = ParentProfile.of(user)
        children.forEach {
            parentProfile.addChild(it.name, it.birthDate, it.gender)
        }
        user.assignParentProfile(parentProfile)
        user.changeActiveRole(UserRoleType.PARENT)
        return user
    }

}
