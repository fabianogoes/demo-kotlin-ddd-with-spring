package br.com.eprogramar.ebank.account.application

import br.com.eprogramar.ebank.account.application.port.`in`.RetrieveAccountUseCase
import br.com.eprogramar.ebank.account.application.port.out.AccountPersistencePort
import br.com.eprogramar.ebank.account.crosscutting.entity.AccountEntity
import br.com.eprogramar.ebank.account.crosscutting.entity.Activity
import br.com.eprogramar.ebank.account.crosscutting.response.ActivityResponse
import br.com.eprogramar.ebank.account.crosscutting.response.RetrieveAccountResponse
import br.com.eprogramar.ebank.account.domain.AccountNotFoundException
import javax.inject.Named

@Named
class RetrieveAccountService(private val accountPersistencePort: AccountPersistencePort): RetrieveAccountUseCase {
    override fun retrieveOne(accountNumber: Int): RetrieveAccountResponse {
        return accountPersistencePort
                .retrieveAccountByNumber(accountNumber)
                .orElseThrow { throw AccountNotFoundException(accountNumber) }
                .let(this::mapToResponse)
    }

    override fun retrieveAll(): List<RetrieveAccountResponse> {
        return accountPersistencePort.retrieveAllAccount().map(this::mapToResponse)
    }

    private fun mapToResponse(entity: AccountEntity) =
            RetrieveAccountResponse(
                    personName = entity.person.name,
                    accountNumber = entity.accountNumber,
                    balance = entity.balance,
                    activities = mapToActivities(entity.activities)
            )

    private fun mapToActivities(activities: MutableList<Activity>) =
            activities.map { ActivityResponse(value = it.value, type = it.type) }
}