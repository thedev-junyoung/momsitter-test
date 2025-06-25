package com.momsitter.infrastructure

import com.momsitter.domain.parent.ParentProfileRepository
import com.momsitter.domain.user.User
import com.momsitter.infrastructure.jpa.ParentProfileJpaRepository
import org.springframework.stereotype.Repository

@Repository
class ParentProfileRepositoryImpl(
    private val parentProfileJpaRepository: ParentProfileJpaRepository
) : ParentProfileRepository {
    override fun deleteByUser(user: User): Int {
        return parentProfileJpaRepository.deleteByUser(user)
    }
}
