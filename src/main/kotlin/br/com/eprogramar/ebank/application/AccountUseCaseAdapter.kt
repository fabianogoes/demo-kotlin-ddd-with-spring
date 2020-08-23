package br.com.eprogramar.ebank.application

import br.com.eprogramar.ebank.crosscutting.ActivityType
import br.com.eprogramar.ebank.domain.Account
import br.com.eprogramar.ebank.domain.AccountDomainService
import br.com.eprogramar.ebank.domain.persistence.AccountPersistencePort
import br.com.eprogramar.ebank.presentation.request.AccountApiRequest
import br.com.eprogramar.ebank.presentation.request.ActivityApiRequest
import javax.inject.Named

@Named
class AccountUseCaseAdapter(private val repository: AccountPersistencePort) : AccountDomainService {

    override fun createNewAccount(request: AccountApiRequest) =
            Account(request.person, request.amount).createNewAccount(repository)

    override fun createNewActivity(
            accountNumber: String,
            activityType: ActivityType,
            request: ActivityApiRequest) = when(activityType) {

        ActivityType.CREDIT -> repository.retrieveAccountByNumber(accountNumber)
                .let { Account(it).createNewActivityOfCreditAndUpdateAmountOfAccount(repository, request.activityValue) }
                .let { repository.retrieveAccountByNumber(accountNumber) }

        ActivityType.DEBIT -> repository.retrieveAccountByNumber(accountNumber)
                .let { Account(it).createNewActivityOfDebitAndUpdateAmountOfAccount(repository, request.activityValue) }
                .let { repository.retrieveAccountByNumber(accountNumber) }
    }

    override fun retrieveAccountByNumber(accountNumber: String) = repository.retrieveAccountByNumber(accountNumber)

    override fun retrieveAllAccount() = repository.retrieveAllAccount()
}