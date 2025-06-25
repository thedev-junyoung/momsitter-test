package com.momsitter.domain.user

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "roles")
open class Role private constructor(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open val id: Long = 0L,

    @Column(unique = true, nullable = false)
    open var name: String
) {
    protected constructor() : this(0L, "DUMMY")

    private constructor(
        name: String
    ) : this() {
        this.name = name.uppercase()
    }

    companion object {
        fun of(name: String): Role {
            require(name.isNotBlank()) { "역할 이름은 비어 있을 수 없습니다." }
            return Role(name.trim().uppercase())
        }
        fun dummy(): Role = Role(name = "DUMMY")

    }


}
