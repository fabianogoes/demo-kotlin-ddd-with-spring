package br.com.eprogramar.ebank.account.application.port.`in`

import br.com.eprogramar.ebank.account.crosscutting.response.RetrieveAccountResponse

interface RetrieveAccountUseCase {
    fun retrieveOne(accountNumber: Int): RetrieveAccountResponse
    fun retrieveAll(): List<RetrieveAccountResponse>
}