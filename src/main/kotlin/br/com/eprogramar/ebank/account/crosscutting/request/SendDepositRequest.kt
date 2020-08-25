package br.com.eprogramar.ebank.account.crosscutting.request

import java.math.BigDecimal

class SendDepositRequest(val accountNumber: Int, val value: BigDecimal)