package br.com.eprogramar.ebank.account.application

import br.com.eprogramar.ebank.account.application.port.`in`.SendDepositUseCase
import br.com.eprogramar.ebank.account.application.port.`in`.SendDepositValidatorPort
import br.com.eprogramar.ebank.account.application.port.out.AccountPersistencePort
import br.com.eprogramar.ebank.account.crosscutting.entity.AccountEntity
import br.com.eprogramar.ebank.account.crosscutting.entity.ActivityEntity
import br.com.eprogramar.ebank.account.crosscutting.request.SendDepositRequest
import br.com.eprogramar.ebank.account.crosscutting.response.SendDepositResponse
import br.com.eprogramar.ebank.account.domain.Account
import br.com.eprogramar.ebank.account.domain.AccountNotFoundException
import br.com.eprogramar.ebank.account.domain.SendDepositAccountNotFoundException
import javax.inject.Named

@Named
class SendDepositService(
        private val accountPersistencePort: AccountPersistencePort,
        private val sendDepositValidatorPort: SendDepositValidatorPort
): SendDepositUseCase {
    override fun execute(sendDepositRequest: SendDepositRequest): SendDepositResponse {
        sendDepositValidatorPort.validateRulesOfValueToDeposit(sendDepositRequest)

        val entityToDeposit = accountPersistencePort
                .retrieveAccountByNumber(sendDepositRequest.accountNumber)
                .orElseThrow { throw SendDepositAccountNotFoundException(sendDepositRequest.accountNumber) }

        return Account(accountNumber = entityToDeposit.accountNumber, balance = entityToDeposit.balance)
                .doDepositAtAccount(sendDepositRequest.value)
                .let { account -> accountPersistencePort.update(
                        entityToDeposit.copy(
                                id = entityToDeposit.id,
                                balance = account.balance,
                                activities = mapToActivityEntity(account, entityToDeposit)
                        )
                )   }.let { entityUpdated -> this.convertEntityToResponse(entityUpdated) }
    }

    private fun mapToActivityEntity(account: Account, entity: AccountEntity) =
        account.activities.map { ActivityEntity(account = entity, value = it.value, type = it.type) }.toMutableList()

    private fun convertEntityToResponse(accountEntity: AccountEntity) = SendDepositResponse(accountEntity.balance)
}