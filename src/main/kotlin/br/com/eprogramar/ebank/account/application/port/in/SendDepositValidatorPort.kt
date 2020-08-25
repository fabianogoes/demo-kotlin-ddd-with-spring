package br.com.eprogramar.ebank.account.application.port.`in`

import br.com.eprogramar.ebank.account.crosscutting.request.SendDepositRequest

interface SendDepositValidatorPort {
    fun validateRulesOfValueToDeposit(sendDepositRequest: SendDepositRequest)
}