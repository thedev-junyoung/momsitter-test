package com.momsitter.domain.child

import com.momsitter.domain.parent.ParentProfile
import com.momsitter.domain.user.Gender
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDate
import java.time.LocalDateTime

@Entity
@Table(name = "children")
open class Child protected constructor() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open var id: Long = 0L
        protected set

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_profile_id", nullable = false)
    open var parentProfile: ParentProfile? = null
        protected set

    @Column(nullable = false)
    open var name: String = "" // 아이 이름
        protected set

    @Column(nullable = false)
    open var birthDate: LocalDate = LocalDate.now()

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    open var gender: Gender = Gender.MALE
        protected set

    @CreationTimestamp
    open var createdAt: LocalDateTime? = null

    @UpdateTimestamp
    open var updatedAt: LocalDateTime = LocalDateTime.now()
        protected set

    private constructor(
        parentProfile: ParentProfile,
        name: String,
        birthDate: LocalDate,
        gender: Gender
    ) : this() {
        this.parentProfile = parentProfile
        this.name = name
        this.birthDate = birthDate
        this.gender = gender
    }

    companion object {
        fun of(parentProfile: ParentProfile, name: String, birthDate: LocalDate, gender: Gender): Child {
            require(name.isNotBlank()) { "아이 이름은 비어있을 수 없습니다." }
            return Child(parentProfile, name, birthDate, gender)
        }
    }


    fun update(name: String?, birthDate: LocalDate?, gender: Gender?) {
        name?.let {
            require(it.isNotBlank()) { "아이 이름은 비어 있을 수 없습니다." }
            this.name = it
        }

        birthDate?.let {
            this.birthDate = it
        }

        gender?.let {
            this.gender = it
        }
    }


}
