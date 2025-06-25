package com.momsitter.domain.care

import com.momsitter.domain.parent.ParentProfile
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
import java.time.LocalDateTime

@Entity
@Table(name = "care_requests")
open class CareRequest private constructor() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open var id: Long = 0L
        protected set

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_profile_id", nullable = false)
    open var parentProfile: ParentProfile? = null
        protected set

    @Column(nullable = false, columnDefinition = "TEXT")
    open var content: String = "" // 신청 내용
        protected set

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    open var status: CareRequestStatus = CareRequestStatus.ACTIVE
        protected set

    @CreationTimestamp
    open var createdAt: LocalDateTime? = null
        protected set

    @UpdateTimestamp
    open var updatedAt: LocalDateTime = LocalDateTime.now()
        protected set

    private constructor(parentProfile: ParentProfile, content: String) : this() {
        this.parentProfile = parentProfile
        this.content = content
        this.status = CareRequestStatus.ACTIVE
    }

    companion object {
        fun of(parentProfile: ParentProfile, content: String): CareRequest {
            require(content.isNotBlank()) { "돌봄 요청 내용은 비어있을 수 없습니다." }
            return CareRequest(parentProfile, content)
        }
    }

    // 비즈니스 메서드
    fun updateContent(content: String) {
        require(content.isNotBlank()) { "돌봄 요청 내용은 비어있을 수 없습니다." }
        this.content = content
    }

    fun markAsMatched() {
        require(status == CareRequestStatus.ACTIVE) { "활성 상태의 요청만 매칭할 수 있습니다." }
        this.status = CareRequestStatus.MATCHED
    }

    fun markAsCompleted() {
        require(status == CareRequestStatus.MATCHED) { "매칭된 요청만 완료할 수 있습니다." }
        this.status = CareRequestStatus.COMPLETED
    }

    fun cancel() {
        require(status in listOf(CareRequestStatus.ACTIVE, CareRequestStatus.MATCHED)) {
            "활성 또는 매칭된 요청만 취소할 수 있습니다."
        }
        this.status = CareRequestStatus.CANCELLED
    }
}

