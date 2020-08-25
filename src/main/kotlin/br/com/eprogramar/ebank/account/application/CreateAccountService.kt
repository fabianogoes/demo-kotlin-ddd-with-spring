package br.com.eprogramar.ebank.account.application

import br.com.eprogramar.ebank.account.application.port.`in`.CreateAccountUseCase
import br.com.eprogramar.ebank.account.application.port.`in`.CreateAccountValidatorPort
import br.com.eprogramar.ebank.account.application.port.out.AccountPersistencePort
import br.com.eprogramar.ebank.account.crosscutting.entity.AccountEntity
import br.com.eprogramar.ebank.account.crosscutting.entity.Person
import br.com.eprogramar.ebank.account.crosscutting.request.CreateAccountRequest
import br.com.eprogramar.ebank.account.crosscutting.response.CreateAccountResponse
import br.com.eprogramar.ebank.account.domain.Account
import br.com.eprogramar.ebank.account.domain.CreateAccountException
import javax.inject.Named

@Named
class CreateAccountService(
        private val accountPersistencePort: AccountPersistencePort,
        private val createAccountValidatorPort: CreateAccountValidatorPort
): CreateAccountUseCase {

    override fun execute(createAccountRequest: CreateAccountRequest): CreateAccountResponse {
        createAccountValidatorPort.validationToNewAccount(createAccountRequest.personName)
        return Account(
                personName = createAccountRequest.personName,
                accountNumber = accountPersistencePort.generateNextAccountNumber()
        ).buildNewAccount().let { account ->
            accountPersistencePort.create(this.convertDomainToEntity(account))
                    .orElseThrow { throw CreateAccountException() }
        }.let(this::convertEntityToResponse)
    }

    private fun convertDomainToEntity(account: Account) =
            AccountEntity(
                person = Person(name = account.personName!!),
                balance = account.balance,
                accountNumber = account.accountNumber
            )

    private fun convertEntityToResponse(accountEntity: AccountEntity) =
            CreateAccountResponse(accountEntity.accountNumber)
}