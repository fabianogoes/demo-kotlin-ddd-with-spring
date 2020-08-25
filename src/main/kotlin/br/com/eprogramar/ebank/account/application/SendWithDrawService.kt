package br.com.eprogramar.ebank.account.application

import br.com.eprogramar.ebank.account.application.port.`in`.SendWithDrawUseCase
import br.com.eprogramar.ebank.account.application.port.`in`.SendWithDrawValidatorPort
import br.com.eprogramar.ebank.account.application.port.out.AccountPersistencePort
import br.com.eprogramar.ebank.account.crosscutting.entity.AccountEntity
import br.com.eprogramar.ebank.account.crosscutting.entity.ActivityEntity
import br.com.eprogramar.ebank.account.crosscutting.request.SendWithDrawRequest
import br.com.eprogramar.ebank.account.crosscutting.response.SendWithDrawResponse
import br.com.eprogramar.ebank.account.domain.Account
import br.com.eprogramar.ebank.account.domain.SendWithDrawAccountNotFoundException
import javax.inject.Named

@Named
class SendWithDrawService(
        private val accountPersistencePort: AccountPersistencePort,
        private val sendWithDrawValidatorPort: SendWithDrawValidatorPort
): SendWithDrawUseCase {
    override fun execute(sendWithDrawRequest: SendWithDrawRequest): SendWithDrawResponse {
        sendWithDrawValidatorPort.validateRulesOfValueToWithDraw(sendWithDrawRequest)

        val entityToWithDraw = accountPersistencePort
                .retrieveAccountByNumber(sendWithDrawRequest.accountNumber)
                .orElseThrow { throw SendWithDrawAccountNotFoundException(sendWithDrawRequest.accountNumber) }

        return Account(accountNumber = entityToWithDraw.accountNumber, balance = entityToWithDraw.balance)
                .doWithDrawAtAccount(sendWithDrawRequest.value)
                .let { account -> accountPersistencePort.update(
                        entityToWithDraw.copy(
                                id = entityToWithDraw.id,
                                balance = account.balance,
                                activities = mapToActivityEntity(account, entityToWithDraw)
                        )
                ) }.let { entityUpdated -> this.convertEntityToResponse(entityUpdated) }
    }

    private fun mapToActivityEntity(accountDomain: Account, accountEntity: AccountEntity) =
            accountDomain.activities.map { ActivityEntity(account = accountEntity, value = it.value, type = it.type) }.toMutableList()

    private fun convertEntityToResponse(accountEntity: AccountEntity) = SendWithDrawResponse(accountEntity.balance)
}