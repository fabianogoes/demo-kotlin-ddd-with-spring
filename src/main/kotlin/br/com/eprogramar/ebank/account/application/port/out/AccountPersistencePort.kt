package br.com.eprogramar.ebank.account.application.port.out

import br.com.eprogramar.ebank.account.crosscutting.entity.AccountEntity
import java.util.*

interface AccountPersistencePort {
    fun create(entity: AccountEntity): Optional<AccountEntity>
    fun generateNextAccountNumber(): Int
    fun retrieveAllAccount(): List<AccountEntity>
    fun retrieveAccountByNumber(accountNumber: Int): Optional<AccountEntity>
    fun update(entity: AccountEntity): AccountEntity
}
