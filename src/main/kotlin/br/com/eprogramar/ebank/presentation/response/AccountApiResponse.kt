package br.com.eprogramar.ebank.presentation.response

import java.math.BigDecimal

class AccountApiResponse(
        val person: String,
        val number: String,
        val amount: BigDecimal,
        val activities: List<ActivityApiResponse>
)