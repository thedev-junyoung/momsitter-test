package com.momsitter.domain.sitter

import com.momsitter.domain.user.User
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime

@Entity
@Table(name = "sitter_profiles")
open class SitterProfile protected constructor() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open var id: Long = 0L
        protected set

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    open var user: User? = null
        protected set

    @Column(nullable = false)
    open var minCareAge: Int = 0 // 케어 가능한 최소 연령
        protected set

    @Column(nullable = false)
    open var maxCareAge: Int = 0 // 케어 가능한 최대 연령
        protected set

    @Column(nullable = false, columnDefinition = "TEXT")
    open var introduction: String = "" // 자기소개
        protected set

    @CreationTimestamp
    open var createdAt: LocalDateTime? = null
        protected set

    @UpdateTimestamp
    open var updatedAt: LocalDateTime = LocalDateTime.now()
        protected set

    private constructor(
        user: User,
        minCareAge: Int,
        maxCareAge: Int,
        introduction: String
    ) : this() {
        this.user = user
        this.minCareAge = minCareAge
        this.maxCareAge = maxCareAge
        this.introduction = introduction
    }

    companion object {
        fun of(user: User, minCareAge: Int, maxCareAge: Int, introduction: String): SitterProfile {
            require(minCareAge <= maxCareAge) { "최소 연령은 최대 연령보다 클 수 없습니다." }
            require(minCareAge > 0) { "케어 연령은 0보다 커야 합니다." }
            return SitterProfile(user, minCareAge, maxCareAge, introduction)
        }
    }

}