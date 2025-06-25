package com.momsitter.domain.care

enum class CareRequestStatus {
    ACTIVE,    // 활성 상태
    MATCHED,   // 매칭됨
    COMPLETED, // 완료
    CANCELLED  // 취소됨
}