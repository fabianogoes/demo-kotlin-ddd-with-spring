package br.com.eprogramar.ebank.account.domain

import br.com.eprogramar.ebank.account.crosscutting.ActivityType
import br.com.eprogramar.ebank.account.crosscutting.entity.Activity
import java.math.BigDecimal

data class Account(
        val personName: String? = null,
        val accountNumber: Int,
        val balance: BigDecimal = BigDecimal(0),
        val activities: MutableList<Activity> = mutableListOf()
) {
    fun buildNewAccount() = this

    fun doDepositAtAccount(value: BigDecimal): Account {
        this.activities.add(Activity(value, ActivityType.DEPOSIT))
        return this.copy(balance = this.balance.plus(value))
    }

    fun doWithDrawAtAccount(value: BigDecimal): Account {
        assert(value.compareTo(this.balance) == -1) { throw RuntimeException("insufficient balance!") }
        this.activities.add(Activity(value, ActivityType.WITHDRAW))
        return this.copy(balance = this.balance.minus(value))
    }
}