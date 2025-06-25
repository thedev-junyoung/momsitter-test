package com.momsitter.infrastructure

import com.momsitter.domain.care.CareRequest
import com.momsitter.domain.care.CareRequestRepository
import com.momsitter.infrastructure.jpa.CareRequestJpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class CareRequestRepositoryImpl(
    private val careRequestJpaRepository: CareRequestJpaRepository
) : CareRequestRepository {
    override fun save(request: CareRequest): CareRequest {
        return careRequestJpaRepository.save(request)
    }

    override fun findById(id: Long): Optional<CareRequest> {
        return careRequestJpaRepository.findById(id)
    }
}