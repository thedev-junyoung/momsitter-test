package com.momsitter.domain.care

import java.util.Optional

interface CareRequestRepository {
    fun save(request: CareRequest): CareRequest
    fun findById(id: Long): Optional<CareRequest>
}
