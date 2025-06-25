package com.momsitter.domain.parent

import com.momsitter.domain.care.CareRequest
import com.momsitter.domain.child.Child
import com.momsitter.domain.user.Gender
import com.momsitter.domain.user.User
import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToMany
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDate
import java.time.LocalDateTime

@Entity
@Table(name = "parent_profiles")
open class ParentProfile protected constructor() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open var id: Long = 0L
        protected set

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    open var user: User? = null
        protected set

    @CreationTimestamp
    open var createdAt: LocalDateTime? = null
        protected set

    @UpdateTimestamp
    open var updatedAt: LocalDateTime = LocalDateTime.now()
        protected set

    // 부모의 아이들
    @OneToMany(mappedBy = "parentProfile", cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.LAZY)
    open var children: MutableList<Child> = mutableListOf()
        protected set

    // 부모의 돌봄 요청들
    @OneToMany(mappedBy = "parentProfile", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    open var careRequests: MutableList<CareRequest> = mutableListOf()
        protected set

    private constructor(user: User) : this() {
        this.user = user
    }

    companion object {
        fun of(user: User): ParentProfile {
            return ParentProfile(user)
        }
    }

    // 비즈니스 메서드
    fun addChild(name: String, birthDate: LocalDate, gender: Gender): Child {
        val child = Child.of(this, name, birthDate, gender)
        children.add(child)
        return child
    }

}