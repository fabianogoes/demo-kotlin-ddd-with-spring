package br.com.eprogramar.ebank.account.application.port.`in`

import br.com.eprogramar.ebank.account.crosscutting.request.SendWithDrawRequest

interface SendWithDrawValidatorPort {
    fun validateRulesOfValueToWithDraw(sendWithDrawRequest: SendWithDrawRequest)
}