package com.momsitter.infrastructure.jpa

import com.momsitter.domain.care.CareRequest
import org.springframework.data.jpa.repository.JpaRepository

interface CareRequestJpaRepository : JpaRepository<CareRequest, Long>{

}
