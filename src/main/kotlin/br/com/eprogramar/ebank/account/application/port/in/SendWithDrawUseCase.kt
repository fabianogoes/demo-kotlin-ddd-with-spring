package br.com.eprogramar.ebank.account.application.port.`in`

import br.com.eprogramar.ebank.account.crosscutting.request.SendWithDrawRequest
import br.com.eprogramar.ebank.account.crosscutting.response.SendWithDrawResponse

interface SendWithDrawUseCase {
    fun execute(sendWithDrawRequest: SendWithDrawRequest): SendWithDrawResponse
}