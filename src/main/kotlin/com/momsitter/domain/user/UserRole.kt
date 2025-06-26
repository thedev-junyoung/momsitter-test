package com.momsitter.domain.user

import jakarta.persistence.*


@Entity
@Table(name = "user_roles")
open class UserRole protected constructor(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open val id: Long = 0L,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    open var user: User? = null,


    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var role: UserRoleType = UserRoleType.DEFAULT
) {
    companion object {
        fun of(user: User, role: UserRoleType): UserRole {
            return UserRole().apply {
                this.user = user
                this.role = role
            }        }
    }

    protected constructor() : this(
        id = 0L,
        user = User.dummy(),
        role = UserRoleType.PARENT // or default
    )
}
