package br.com.eprogramar.ebank.infrastructure.repository

import br.com.eprogramar.ebank.infrastructure.entity.AccountEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

internal interface AccountRepository : JpaRepository<AccountEntity, Long> {

    fun findByNumber(number: String): Optional<AccountEntity>

}