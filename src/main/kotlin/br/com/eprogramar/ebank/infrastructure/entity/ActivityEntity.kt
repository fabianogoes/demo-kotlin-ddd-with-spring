package br.com.eprogramar.ebank.infrastructure.entity

import br.com.eprogramar.ebank.crosscutting.ActivityType
import java.math.BigDecimal
import javax.persistence.*

@Entity
class ActivityEntity(
        @JoinColumn
        @ManyToOne(fetch = FetchType.LAZY)
        val account: AccountEntity,
        val value: BigDecimal,
        @Enumerated(EnumType.STRING)
        val type: ActivityType
) : AuditEntityBase()