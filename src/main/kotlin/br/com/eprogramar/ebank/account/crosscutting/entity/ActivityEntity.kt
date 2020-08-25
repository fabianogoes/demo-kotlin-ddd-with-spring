package br.com.eprogramar.ebank.account.crosscutting.entity

import br.com.eprogramar.ebank.account.crosscutting.ActivityType
import java.math.BigDecimal
import javax.persistence.*

@Entity
class ActivityEntity(
        @JoinColumn
        @ManyToOne(fetch = FetchType.LAZY)
        val account: AccountEntity? = null,
        val value: BigDecimal,
        @Enumerated(EnumType.STRING)
        val type: ActivityType
) : AuditEntityBase()