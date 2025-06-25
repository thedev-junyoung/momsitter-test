package com.momsitter.domain.user

import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

@Entity
@Table(name = "user_roles")
open class UserRole private constructor(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open val id: Long = 0L,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    open val user: User,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", nullable = false)
    open val role: Role
) {
    companion object {
        fun of(user: User, role: Role): UserRole {
            return UserRole(user = user, role = role)
        }
    }

    protected constructor() : this(
        id = 0L,
        user = User.dummy(),
        role = Role.dummy()
    )}
