package br.com.eprogramar.ebank.account.crosscutting.response

import br.com.eprogramar.ebank.account.crosscutting.ActivityType
import java.math.BigDecimal

class RetrieveAccountResponse(
        val personName: String,
        val accountNumber: Int,
        val balance: BigDecimal,
        val activities: List<ActivityResponse>
)

class ActivityResponse(
        val value: BigDecimal,
        val type: ActivityType
)