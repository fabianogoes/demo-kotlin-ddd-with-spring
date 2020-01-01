package com.example.demo.boundaries.account.infrastructure

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.io.Serializable
import java.util.*
import javax.persistence.*

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class AuditBase(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long? = null,
        @CreatedDate
        @Temporal(TemporalType.TIMESTAMP)
        var createdAt: Date? = null,
        @LastModifiedDate
        @Temporal(TemporalType.TIMESTAMP)
        var modifiedAt: Date? = null
) : Serializable