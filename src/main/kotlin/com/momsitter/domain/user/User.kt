package com.momsitter.domain.user

import com.momsitter.common.BusinessException
import com.momsitter.common.ErrorCode
import com.momsitter.domain.child.ChildInfo
import com.momsitter.domain.parent.ParentProfile
import com.momsitter.domain.sitter.SitterProfile
import com.momsitter.domain.sitter.SitterProfileInfo
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

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = [JoinColumn(name = "user_id")])
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    open val roles: MutableSet<UserRoleType> = mutableSetOf()


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
        fun signUpAsSitter(
            username: String,
            password: String,
            name: String,
            birthDate: LocalDate,
            gender: Gender,
            email: String,
            activeRole: UserRoleType,
            role: UserRoleType,
            sitterInfo: SitterProfileInfo
        ): User {
            val user = User(
                username = username,
                password = password,
                name = name,
                birthDate = birthDate,
                gender = gender,
                email = email,
                activeRole = activeRole
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
            activeRole: UserRoleType,
            role: UserRoleType
        ): User {
            val user = User(
                username = username,
                password = password,
                name = name,
                birthDate = birthDate,
                gender = gender,
                email = email,
                activeRole = activeRole
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
            role: UserRoleType,
            activeRole: UserRoleType,
            children: List<ChildInfo>
        ): User {
            val user = User(
                username = username,
                password = password,
                name = name,
                birthDate = birthDate,
                gender = gender,
                email = email,
                activeRole = activeRole

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
            email = "dummy@dummy.com",
            activeRole = UserRoleType.DEFAULT
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

    fun assignRole(role: UserRoleType) {
        roles.add(role)
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

    fun hasRole(roleType: UserRoleType): Boolean {
        return roles.contains(roleType)
    }

    fun addRole(role: UserRole) {
        if (this.hasRole(role.role)) {
            throw BusinessException("이미 해당 역할을 가지고 있습니다.", ErrorCode.DUPLICATE_ROLE)
        }
        this.roles.add(role.role)
    }

    fun extendToSitter(minAge: Int, maxAge: Int, introduction: String) {
        if (this.hasRole(UserRoleType.SITTER)) {
            throw BusinessException("이미 시터 역할을 가지고 있습니다.", ErrorCode.DUPLICATE_ROLE)
        }

        val sitterProfile = SitterProfile.of(this, minAge, maxAge, introduction)
        this.addRole(UserRole.of(this, UserRoleType.SITTER))
        this.sitterProfile = sitterProfile
    }


}

