package com.example.demo.boundaries.account.infrastructure.entity

import com.example.demo.boundaries.account.crosscutting.MovementType
import com.example.demo.boundaries.account.infrastructure.entity.AccountEntity
import com.example.demo.boundaries.account.infrastructure.entity.AuditEntityBase
import java.math.BigDecimal
import javax.persistence.*

@Entity
class MovementEntity(
        @JoinColumn
        @ManyToOne(fetch = FetchType.LAZY)
        val account: AccountEntity,
        val value: BigDecimal,
        @Enumerated(EnumType.STRING)
        val type: MovementType
) : AuditEntityBase()