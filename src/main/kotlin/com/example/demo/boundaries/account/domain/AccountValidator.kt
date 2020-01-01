package com.example.demo.boundaries.account.domain

import org.springframework.util.Assert
import java.math.BigDecimal

internal class AccountValidator(private val account: Account) {

    fun validateRulesOfPerson() {
        Assert.notNull(account.personName, "[PersonName] should not be Null!")
        Assert.hasText(account.personName, "[PersonName] should not be Black!")
        Assert.isTrue(account.personName.length >= 5, "[PersonName] should has greater than 4 words!")
    }

    fun validateRulesOfAmount() {
        Assert.notNull(account.amount, "[Amount] should not be Null!")
        val amountIsGreaterOrEqualsZero =
                account.amount.toDouble().compareTo(0) == 1 ||
                account.amount.toDouble().compareTo(0) == 0
        Assert.isTrue(amountIsGreaterOrEqualsZero, "[Amount] should be Less than ZERO!")
    }

    fun validateRulesOfValueToNewMovement(movementValue: BigDecimal) {
        Assert.notNull(movementValue, "[Movement Value] should not be Null!")
        Assert.isTrue(account.amount.toDouble().compareTo(0) == 1, "[Movement Value] should be Greater than ZERO!")
    }

    fun validateRulesOfSufficientAmountToNewMovementOfDebit(movementValue: BigDecimal) {
        val amountIsGreaterOrEqualsMovementValue: Boolean =
                account.amount.toDouble().compareTo(movementValue.toDouble()) == 1 ||
                account.amount.toDouble().compareTo(movementValue.toDouble()) == 0
        Assert.isTrue(amountIsGreaterOrEqualsMovementValue, "[Movement Value] Account has not Sufficient Amount to Register this Debit")
    }

}