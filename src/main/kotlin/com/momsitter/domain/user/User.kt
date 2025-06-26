package com.momsitter.domain.user

import com.momsitter.common.BusinessException
import com.momsitter.common.ErrorCode
import com.momsitter.domain.child.ChildInfo
import com.momsitter.domain.parent.ParentProfile
import com.momsitter.domain.sitter.SitterProfile
import jakarta.persistence.*
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

    @Column(nullable = false)
    open var activeRole : UserRoleType = UserRoleType.DEFAULT
        protected set

    @CreationTimestamp
    open var createdAt: LocalDateTime? = null
        protected set

    @UpdateTimestamp
    open var updatedAt: LocalDateTime = LocalDateTime.now()
        protected set

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.LAZY)
    open val userRoles: MutableSet<UserRole> = mutableSetOf()



    // 양방향 관계
    @OneToOne(mappedBy = "user", cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.LAZY)
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
        email: String,
        activeRole: UserRoleType
    ) : this() {
        this.username = username
        this.password = password
        this.name = name
        this.birthDate = birthDate
        this.gender = gender
        this.email = email
        this.activeRole = activeRole
    }

    companion object {

        fun dummy(): User = User(
            username = "dummy",
            password = "dummy",
            name = "dummy",
            birthDate = LocalDate.now(),
            gender = Gender.MALE,
            email = "dummy@dummy.com",
            activeRole = UserRoleType.DEFAULT
        )

        fun basicUser(
            username: String,
            password: String,
            name: String,
            birthDate: LocalDate,
            gender: Gender,
            email: String,
        ) : User {
            return User(
                username = username,
                password = password,
                name = name,
                birthDate = birthDate,
                gender = gender,
                email = email,
                activeRole = UserRoleType.DEFAULT

            )


        }
    }

    // 비즈니스 메서드


    fun isSitter(): Boolean = sitterProfile != null
    fun isParent(): Boolean = parentProfile != null


    fun changePassword(newPassword: String) {
        if (newPassword.isBlank()) {
            throw BusinessException("비밀번호는 빈 문자열일 수 없습니다.", ErrorCode.INVALID_PASSWORD)
        }
        this.password = newPassword
    }

    fun updateInfo(name: String?, email: String?) {
        if (name != null) {
            if (name.isBlank()) throw BusinessException("이름은 빈 문자열일 수 없습니다.", ErrorCode.INVALID_NAME)
            this.name = name
        }

        if (email != null) {
            if (email.isBlank()) throw BusinessException("이메일은 빈 문자열일 수 없습니다.", ErrorCode.INVALID_EMAIL)
            this.email = email
        }
    }
    fun changeActiveRole(role: UserRoleType) {
        if (!hasRole(role)) {
            throw BusinessException("해당 역할이 없습니다.", ErrorCode.ROLE_NOT_FOUND)
        }
        this.activeRole = role
    }



    fun hasRole(role: UserRoleType): Boolean {
        return userRoles.any { it.role == role }
    }

    fun addRole(role: UserRoleType) {
        if (hasRole(role)) throw BusinessException("이미 존재하는 역할입니다.", ErrorCode.DUPLICATE_ROLE)
        this.userRoles.add(UserRole.of(this, role))
    }

    fun removeRole(role: UserRoleType) {
        this.userRoles.removeIf { it.role == role }
    }


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

    fun assignSitterProfile(profile: SitterProfile) {
        this.sitterProfile = profile
        if (!this.userRoles.contains(UserRole.of(this, UserRoleType.SITTER))) {
            this.addRole(UserRoleType.SITTER)
            this.activeRole = UserRoleType.SITTER
        }
    }
    fun assignParentProfile(profile: ParentProfile) {
        this.parentProfile = profile
        if (!this.userRoles.contains(UserRole.of(this, UserRoleType.PARENT))) {
            this.addRole(UserRoleType.PARENT)
            this.activeRole = UserRoleType.PARENT
        }
    }

    fun extendToSitter(minAge: Int, maxAge: Int, introduction: String) {
        if (this.hasRole(UserRoleType.SITTER)) {
            throw BusinessException("이미 시터 역할을 가지고 있습니다.", ErrorCode.DUPLICATE_ROLE)
        }

        val sitterProfile = SitterProfile.of(this, minAge, maxAge, introduction)
        this.addRole( UserRoleType.SITTER)
        this.sitterProfile = sitterProfile
    }

    fun extendToParent(children: List<ChildInfo>) {
        if (!hasRole(UserRoleType.PARENT)) {
            this.addRole( UserRoleType.PARENT)
        }

        if (this.parentProfile == null) {
            this.parentProfile = ParentProfile.of(this)
        }
        changeActiveRole(UserRoleType.PARENT)

        children.forEach { info ->
            this.parentProfile!!.addChild(info.name, info.birthDate, info.gender)
        }

    }



}

