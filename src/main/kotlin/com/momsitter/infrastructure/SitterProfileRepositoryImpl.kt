package com.momsitter.infrastructure

import com.momsitter.domain.sitter.SitterProfileRepository
import com.momsitter.domain.user.User
import com.momsitter.infrastructure.jpa.SitterProfileJpaRepository
import org.springframework.stereotype.Repository

@Repository
class SitterProfileRepositoryImpl(
    private val sitterProfileJpaRepository: SitterProfileJpaRepository
) : SitterProfileRepository {


    override fun deleteByUser(user: User): Int {
        return sitterProfileJpaRepository.deleteByUser(user)
    }
}
