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
    var id: Long = 0L
        protected set

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_profile_id", nullable = false)
    var parentProfile: ParentProfile? = null
        protected set

    @Column(nullable = false)
    var name: String = "" // 아이 이름
        protected set

    @Column(nullable = false)
    var birthDate: LocalDate = LocalDate.now()

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var gender: Gender = Gender.MALE
        protected set

    @CreationTimestamp
    var createdAt: LocalDateTime? = null

    @UpdateTimestamp
    var updatedAt: LocalDateTime = LocalDateTime.now()
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

}
