package com.momsitter.infrastructure.jpa

import com.momsitter.domain.care.CareRequest
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.*

interface CareRequestJpaRepository : JpaRepository<CareRequest, Long>{
    @Query("SELECT cr FROM CareRequest cr JOIN FETCH cr.parentProfile pp JOIN FETCH pp.user WHERE cr.id = :id")
    fun findWithParentAndUserById(@Param("id") id: Long): Optional<CareRequest>

}
