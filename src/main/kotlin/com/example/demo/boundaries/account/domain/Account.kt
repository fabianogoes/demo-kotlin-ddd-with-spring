package com.example.demo.boundaries.account.domain

import com.example.demo.boundaries.account.infrastructure.AccountRepository
import java.math.BigDecimal

class Account() {

    lateinit var personName: String
    lateinit var amount: BigDecimal
    lateinit var number: String
    private lateinit var validator: AccountValidator

    constructor(personName: String, amount: BigDecimal) : this() {
        this.personName = personName
        this.amount = amount
        this.validator = AccountValidator(this)
    }

    constructor(accountVO: AccountVO) : this() {
        number = accountVO.number
        personName = accountVO.personName
        amount = accountVO.amount
        validator = AccountValidator(this)
        this.validator = AccountValidator(this)
    }

    enum class MovementType {
        CREDIT, DEBIT
    }

    fun createNewAccount(repository: AccountRepository): AccountVO {
        this.isNewAccountValid()
        this.number = this.generateNewAccountNumber(repository)
        val accountVo = toAccountVo(this)
        repository.persisNewAccount(accountVo)
        accountVo.movements = repository.retrieveAllMovements(this.number)
        return accountVo
    }

    fun createNewMovementOfCreditAndUpdateAmountOfAccount(repository: AccountRepository, movementValue: BigDecimal) {
        isNewMovementOfCreditValid(movementValue)
        this.updateCreditAmountOfAccount(movementValue)

        val accountVO = this.toAccountVo(this)
        val movementVO = this.toMovementOfCredit(movementValue)

        repository.persistNewMovementAndUpdateAmount(accountVO, movementVO)
    }

    fun createNewMovementOfDebitAndUpdateAmountOfAccount(repository: AccountRepository, movementValue: BigDecimal) {
        isNewMovementOfDebitValid(movementValue)
        updateDebitAmountOfAccount(movementValue)

        val accountVO = this.toAccountVo(this)
        val movementVO = this.toMovementOfDebit(movementValue)

        repository.persistNewMovementAndUpdateAmount(accountVO, movementVO)
    }

    private fun updateCreditAmountOfAccount(movementValue: BigDecimal) {
        amount = amount.add(movementValue)
    }

    private fun updateDebitAmountOfAccount(movementValue: BigDecimal) {
        amount = amount.subtract(movementValue)
    }

    private fun isNewAccountValid() {
        this.validator.validateRulesOfPerson()
        this.validator.validateRulesOfAmount()
    }

    private fun isNewMovementOfCreditValid(movementValue: BigDecimal) {
        validator.validateRulesOfValueToNewMovement(movementValue)
    }

    private fun isNewMovementOfDebitValid(movementValue: BigDecimal) {
        validator.validateRulesOfValueToNewMovement(movementValue)
        validator.validateRulesOfSufficientAmountToNewMovementOfDebit(movementValue)
    }

    private fun toAccountVo(account: Account): AccountVO {
        return AccountVO(
                personName = account.personName,
                number = account.number,
                amount = account.amount,
                movements = mutableListOf()
        )
    }

    private fun toMovementOfDebit(movementValue: BigDecimal): MovementVO {
        return MovementVO(movementValue, MovementType.DEBIT.name)
    }

    private fun toMovementOfCredit(movementValue: BigDecimal): MovementVO {
        return MovementVO(movementValue, MovementType.CREDIT.name)
    }

    private fun generateNewAccountNumber(repository: AccountRepository): String {
        val nextNumber: Int = repository.generateNextAccountNumber()
        val lengthNumber = 10
        return String.format("%1$" + lengthNumber + "s", nextNumber).replace(' ', '0')
    }


}