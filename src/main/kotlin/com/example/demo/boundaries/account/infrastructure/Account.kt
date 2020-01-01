package com.example.demo.boundaries.account.infrastructure

import java.math.BigDecimal
import javax.persistence.*

@Entity
class Account(
        @ManyToOne(cascade = [CascadeType.ALL])
        val person: Person,
        var amount: BigDecimal,
        val number: String,
        @OneToMany(mappedBy = "account", fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
        var movements: MutableList<Movement> = mutableListOf()
) : AuditBase()