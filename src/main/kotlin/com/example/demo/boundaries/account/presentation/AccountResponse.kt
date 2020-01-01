package com.example.demo.boundaries.account.presentation

import com.example.demo.boundaries.account.domain.MovementVO
import java.math.BigDecimal

class AccountResponse(
        val person: String,
        val number: String,
        val amount: BigDecimal,
        val movements: List<MovementResponse>
)