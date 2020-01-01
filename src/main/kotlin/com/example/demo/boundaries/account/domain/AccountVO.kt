package com.example.demo.boundaries.account.domain

import java.math.BigDecimal

class AccountVO(
        val personName: String,
        val number: String,
        val amount: BigDecimal,
        var movements: List<MovementVO>
)