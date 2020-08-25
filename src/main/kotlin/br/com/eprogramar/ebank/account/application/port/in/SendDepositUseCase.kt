package br.com.eprogramar.ebank.account.application.port.`in`

import br.com.eprogramar.ebank.account.crosscutting.request.SendDepositRequest
import br.com.eprogramar.ebank.account.crosscutting.response.SendDepositResponse

interface SendDepositUseCase {
    fun execute(sendDepositRequest: SendDepositRequest): SendDepositResponse
}