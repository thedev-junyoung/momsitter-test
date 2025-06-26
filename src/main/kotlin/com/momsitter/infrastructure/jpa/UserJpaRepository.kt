package com.momsitter.infrastructure.jpa

import com.momsitter.domain.user.User
import com.momsitter.domain.user.UserRoleType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface UserJpaRepository : JpaRepository<User, Long> {
    fun existsByUsername(username: String): Boolean
    fun existsByEmail(email: String): Boolean
    fun findByUsername(username: String): User?

    @Query("""
        SELECT u FROM User u
        LEFT JOIN FETCH u.sitterProfile
        WHERE u.id = :userId 
    """)
    fun findSitterUser(@Param("userId") userId: Long): User?

    @Query("""
    SELECT u FROM User u
    LEFT JOIN FETCH u.parentProfile pp
    LEFT JOIN FETCH pp.children
    WHERE u.id = :userId
""")
    fun findUserWithParentAndChildren(@Param("userId") userId: Long): User?

    @Query("SELECT u.activeRole FROM User u WHERE u.id = :userId")
    fun getUserRole(@Param("userId") userId: Long): UserRoleType?

    @Query("""
    SELECT u FROM User u
    LEFT JOIN FETCH u.sitterProfile sp
    WHERE u.id = :userId
""")
    fun findUserWithSitterProfile(@Param("userId") userId: Long): User?


}
