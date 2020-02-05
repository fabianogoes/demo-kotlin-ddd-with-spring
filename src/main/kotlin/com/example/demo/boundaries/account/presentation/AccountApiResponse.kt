package com.example.demo.boundaries.account.presentation

import java.math.BigDecimal

class AccountApiResponse(
        val person: String,
        val number: String,
        val amount: BigDecimal,
        val movements: List<MovementApiResponse>
)