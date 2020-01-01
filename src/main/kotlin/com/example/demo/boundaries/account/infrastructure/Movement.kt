package com.example.demo.boundaries.account.infrastructure

import java.math.BigDecimal
import javax.persistence.*

@Entity
class Movement(
        @JoinColumn
        @ManyToOne(fetch = FetchType.LAZY)
        val account: Account,
        val value: BigDecimal,
        @Enumerated(EnumType.STRING)
        val type: MovementType
) : AuditBase()