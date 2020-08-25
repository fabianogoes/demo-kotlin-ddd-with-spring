package br.com.eprogramar.ebank.account.application

import br.com.eprogramar.ebank.account.application.port.`in`.SendWithDrawUseCase
import br.com.eprogramar.ebank.account.application.port.`in`.SendWithDrawValidatorPort
import br.com.eprogramar.ebank.account.application.port.out.AccountPersistencePort
import br.com.eprogramar.ebank.account.crosscutting.ActivityType
import br.com.eprogramar.ebank.account.crosscutting.entity.AccountEntity
import br.com.eprogramar.ebank.account.crosscutting.entity.Activity
import br.com.eprogramar.ebank.account.crosscutting.request.SendWithDrawRequest
import br.com.eprogramar.ebank.account.crosscutting.response.SendWithDrawResponse
import br.com.eprogramar.ebank.account.domain.Account
import br.com.eprogramar.ebank.account.domain.SendDepositAccountNotFoundException
import javax.inject.Named

@Named
class SendWithDrawService(
        private val accountPersistencePort: AccountPersistencePort,
        private val sendWithDrawValidatorPort: SendWithDrawValidatorPort
): SendWithDrawUseCase {
    override fun execute(sendWithDrawRequest: SendWithDrawRequest): SendWithDrawResponse {
        sendWithDrawValidatorPort.validateRulesOfValueToWithDraw(sendWithDrawRequest)

        val accountEntity = accountPersistencePort
                .retrieveAccountByNumber(sendWithDrawRequest.accountNumber)
                .orElseThrow { throw SendDepositAccountNotFoundException(sendWithDrawRequest.accountNumber) }

        return mapToDomain(accountEntity)
                .doWithDrawAtAccount(sendWithDrawRequest.value)
                .let { accountDomain -> accountPersistencePort.update(copyEntity(accountEntity, accountDomain)) }
                .let { entityUpdated -> SendWithDrawResponse(entityUpdated.balance) }
    }

    private fun mapToDomain(accountEntity: AccountEntity) =
            Account(accountNumber = accountEntity.accountNumber, balance = accountEntity.balance, activities = accountEntity.activities)

    private fun copyEntity(entityToDeposit: AccountEntity, account: Account) =
            entityToDeposit.copy(id = entityToDeposit.id, balance = account.balance, activities = account.activities)
}