package com.example.demo.boundaries.account.domain.vo

import java.math.BigDecimal

data class AccountVO(
        val personName: String,
        val number: String,
        val amount: BigDecimal,
        var movements: List<MovementVO>
)