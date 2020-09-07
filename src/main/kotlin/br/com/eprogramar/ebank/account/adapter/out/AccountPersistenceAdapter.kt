package br.com.eprogramar.ebank.account.adapter.out

import br.com.eprogramar.ebank.account.application.port.out.AccountPersistencePort
import br.com.eprogramar.ebank.account.crosscutting.entity.AccountEntity
import org.springframework.stereotype.Repository
import java.util.*

@Repository
internal class AccountPersistenceAdapter(
        private val springDataAccountRepository: SpringDataAccountRepository
) : AccountPersistencePort {

    override fun create(entity: AccountEntity): Optional<AccountEntity> =
            Optional.of(springDataAccountRepository.save(entity))

    override fun generateNextAccountNumber(): Int = springDataAccountRepository.findAll()
            .ifEmpty { return 1 }
            .maxBy { it.accountNumber }?.accountNumber?.plus(1) ?: 1

    override fun retrieveAccountByNumber(accountNumber: Int): Optional<AccountEntity> =
        springDataAccountRepository.findByAccountNumber(accountNumber)

    override fun update(entity: AccountEntity): AccountEntity = springDataAccountRepository.save(entity)

    override fun retrieveAllAccount(): List<AccountEntity> = springDataAccountRepository.findAll()
}