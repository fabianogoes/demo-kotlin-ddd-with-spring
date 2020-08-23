package com.example.demo.boundaries.account.infrastructure.entity

import java.math.BigDecimal
import javax.persistence.*

@Entity
class AccountEntity(
        @ManyToOne(cascade = [CascadeType.ALL])
        val person: PersonEntity,
        var amount: BigDecimal,
        val number: String,
        @OneToMany(mappedBy = "account", fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
        var movements: MutableList<MovementEntity> = mutableListOf()
) : AuditEntityBase()