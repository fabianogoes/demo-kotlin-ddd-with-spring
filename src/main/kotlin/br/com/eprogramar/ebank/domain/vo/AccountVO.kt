package br.com.eprogramar.ebank.domain.vo

import java.math.BigDecimal

data class AccountVO(
        val personName: String,
        val number: String,
        val amount: BigDecimal,
        var activities: List<ActivityVO>
)