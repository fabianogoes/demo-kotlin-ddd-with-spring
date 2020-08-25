package br.com.eprogramar.ebank.account.crosscutting.entity

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.io.Serializable
import java.util.*
import javax.persistence.*

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class AuditEntityBase(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long? = null,
        @CreatedDate
        @Temporal(TemporalType.TIMESTAMP)
        var createdAt: Date? = null,
        @LastModifiedDate
        @Temporal(TemporalType.TIMESTAMP)
        var modifiedAt: Date? = null
) : Serializable