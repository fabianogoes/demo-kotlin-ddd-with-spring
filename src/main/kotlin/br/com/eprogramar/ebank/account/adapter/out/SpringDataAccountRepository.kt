package br.com.eprogramar.ebank.account.adapter.out

import br.com.eprogramar.ebank.account.crosscutting.entity.AccountEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

internal interface SpringDataAccountRepository : JpaRepository<AccountEntity, Long> {

    fun findByAccountNumber(accountNumber: Int): Optional<AccountEntity>

}