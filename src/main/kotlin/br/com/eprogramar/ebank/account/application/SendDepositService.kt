package br.com.eprogramar.ebank.account.application

import br.com.eprogramar.ebank.account.application.port.`in`.SendDepositUseCase
import br.com.eprogramar.ebank.account.application.port.`in`.SendDepositValidatorPort
import br.com.eprogramar.ebank.account.application.port.out.AccountPersistencePort
import br.com.eprogramar.ebank.account.crosscutting.entity.AccountEntity
import br.com.eprogramar.ebank.account.crosscutting.request.SendDepositRequest
import br.com.eprogramar.ebank.account.crosscutting.response.SendDepositResponse
import br.com.eprogramar.ebank.account.domain.Account
import br.com.eprogramar.ebank.account.domain.SendDepositAccountNotFoundException
import javax.inject.Named

@Named
class SendDepositService(
        private val accountPersistencePort: AccountPersistencePort,
        private val sendDepositValidatorPort: SendDepositValidatorPort
): SendDepositUseCase {
    override fun execute(sendDepositRequest: SendDepositRequest): SendDepositResponse {
        sendDepositValidatorPort.validateRulesOfValueToDeposit(sendDepositRequest)

        val accountEntity = accountPersistencePort
                .retrieveAccountByNumber(sendDepositRequest.accountNumber)
                .orElseThrow { throw SendDepositAccountNotFoundException(sendDepositRequest.accountNumber) }

        return mapToDomain(accountEntity)
                .doDepositAtAccount(sendDepositRequest.value)
                .let { accountDomain -> accountPersistencePort.update(copyEntity(accountEntity, accountDomain)) }
                .let { entityUpdated -> SendDepositResponse(entityUpdated.balance) }
    }

    private fun mapToDomain(accountEntity: AccountEntity) =
            Account(accountNumber = accountEntity.accountNumber, balance = accountEntity.balance, activities = accountEntity.activities)

    private fun copyEntity(entityToDeposit: AccountEntity, account: Account) =
            entityToDeposit.copy(id = entityToDeposit.id, balance = account.balance, activities = account.activities)
}