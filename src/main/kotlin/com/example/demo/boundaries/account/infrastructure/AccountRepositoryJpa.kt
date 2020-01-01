package com.example.demo.boundaries.account.infrastructure

import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

internal interface AccountRepositoryJpa : JpaRepository<Account, Long> {

    fun findByNumber(number: String): Optional<Account>

}