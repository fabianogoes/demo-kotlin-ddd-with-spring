package com.example.demo.boundaries.account.infrastructure.repository

import com.example.demo.boundaries.account.infrastructure.entity.AccountEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

internal interface AccountRepository : JpaRepository<AccountEntity, Long> {

    fun findByNumber(number: String): Optional<AccountEntity>

}