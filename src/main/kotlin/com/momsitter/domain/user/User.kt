package com.momsitter.domain.user

import com.momsitter.domain.child.ChildInfo
import com.momsitter.domain.parent.ParentProfile
import com.momsitter.domain.sitter.SitterProfile
import com.momsitter.domain.sitter.SitterProfileInfo
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDate
import java.time.LocalDateTime

@Entity
@Table(name = "users")
open class User protected constructor() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open var id: Long = 0L
        protected set

    @Column(unique = true, nullable = false)
    open var username: String = ""
        protected set

    @Column(nullable = false)
    open var password: String = ""
        protected set

    @Column(nullable = false)
    open var name: String = ""
        protected set

    @Column(nullable = false)
    open var birthDate: LocalDate? = null
        protected set

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    open var gender: Gender = Gender.MALE
        protected set

    @Column(unique = true, nullable = false)
    open var email: String = ""
        protected set

    @CreationTimestamp
    open var createdAt: LocalDateTime? = null
        protected set

    @UpdateTimestamp
    open var updatedAt: LocalDateTime = LocalDateTime.now()
        protected set

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], orphanRemoval = true)
    open val userRoles: MutableList<UserRole> = mutableListOf()

    // 양방향 관계
    @OneToOne(mappedBy = "user", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    open var sitterProfile: SitterProfile? = null
        protected set

    @OneToOne(mappedBy = "user", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    open var parentProfile: ParentProfile? = null
        protected set

    private constructor(
        username: String,
        password: String,
        name: String,
        birthDate: LocalDate,
        gender: Gender,
        email: String
    ) : this() {
        this.username = username
        this.password = password
        this.name = name
        this.birthDate = birthDate
        this.gender = gender
        this.email = email
    }

    companion object {
        fun signUpAsSitter(
            username: String,
            password: String,
            name: String,
            birthDate: LocalDate,
            gender: Gender,
            email: String,
            role: Role,
            sitterInfo: SitterProfileInfo
        ): User {
            val user = User(
                username = username,
                password = password,
                name = name,
                birthDate = birthDate,
                gender = gender,
                email = email
            )
            user.assignRole(role)
            user.becomeSitter(sitterInfo.minCareAge, sitterInfo.maxCareAge, sitterInfo.introduction)
            return user
        }

        fun signUpAsParentOnly(
            username: String,
            password: String,
            name: String,
            birthDate: LocalDate,
            gender: Gender,
            email: String,
            role: Role
        ): User {
            val user = User(
                username = username,
                password = password,
                name = name,
                birthDate = birthDate,
                gender = gender,
                email = email
            )
            user.assignRole(role)
            user.becomeParent()
            return user
        }

        fun signUpAsParentWithChildren(
            username: String,
            password: String,
            name: String,
            birthDate: LocalDate,
            gender: Gender,
            email: String,
            role: Role,
            children: List<ChildInfo>
        ): User {
            val user = User(
                username = username,
                password = password,
                name = name,
                birthDate = birthDate,
                gender = gender,
                email = email
            )
            user.assignRole(role)
            user.becomeParent()
            val parentProfile = user.parentProfile ?: throw IllegalStateException("부모 프로필이 생성되지 않았습니다.")
            children.forEach {
                parentProfile.addChild(it.name, it.birthDate, it.gender)
            }
            return user
        }
        fun dummy(): User = User(
            username = "dummy",
            password = "dummy",
            name = "dummy",
            birthDate = LocalDate.now(),
            gender = Gender.MALE,
            email = "dummy@dummy.com"
        )
    }

    // 비즈니스 메서드
    fun becomeSitter(minCareAge: Int, maxCareAge: Int, introduction: String) {
        if (sitterProfile == null) {
            sitterProfile = SitterProfile.of(this, minCareAge, maxCareAge, introduction)
        }
    }

    fun becomeParent() {
        if (parentProfile == null) {
            parentProfile = ParentProfile.of(this)
        }
    }

    fun assignRole(role: Role) {
        if (userRoles.none { it.role.name == role.name }) {
            userRoles.add(UserRole.of(this, role))
        }
    }

    fun updatePassword(newPassword: String) {
        this.password = newPassword
    }

    fun updateProfile(name: String, birthDate: LocalDate, gender: Gender, email: String) {
        this.name = name
        this.birthDate = birthDate
        this.gender = gender
        this.email = email
    }



    fun isSitter(): Boolean = sitterProfile != null
    fun isParent(): Boolean = parentProfile != null

    fun hasRole(roleName: String): Boolean {
        return userRoles.any { it.role.name.equals(roleName, ignoreCase = true) }
    }
}

