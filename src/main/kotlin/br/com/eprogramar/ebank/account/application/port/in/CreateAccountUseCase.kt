package br.com.eprogramar.ebank.account.application.port.`in`

import br.com.eprogramar.ebank.account.crosscutting.request.CreateAccountRequest
import br.com.eprogramar.ebank.account.crosscutting.response.CreateAccountResponse

interface CreateAccountUseCase {
    fun execute(createAccountRequest: CreateAccountRequest): CreateAccountResponse
}