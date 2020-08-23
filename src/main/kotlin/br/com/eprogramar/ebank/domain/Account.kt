package br.com.eprogramar.ebank.domain

import br.com.eprogramar.ebank.crosscutting.ActivityType
import br.com.eprogramar.ebank.domain.persistence.AccountPersistencePort
import br.com.eprogramar.ebank.domain.validator.AccountValidator
import br.com.eprogramar.ebank.domain.vo.AccountVO
import br.com.eprogramar.ebank.domain.vo.ActivityVO
import java.math.BigDecimal

class Account() {

    lateinit var personName: String
    lateinit var amount: BigDecimal

    private lateinit var number: String
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

    fun createNewAccount(repository: AccountPersistencePort): AccountVO {
        this.isNewAccountValid()
        this.number = this.generateNewAccountNumber(repository)
        val accountVo = toAccountVo(this)
        repository.persisNewAccount(accountVo)
        accountVo.activities = repository.retrieveAllActivities(this.number)
        return accountVo
    }

    fun createNewActivityOfCreditAndUpdateAmountOfAccount(repository: AccountPersistencePort, activityValue: BigDecimal) {
        isNewActivityOfCreditValid(activityValue)
        this.updateCreditAmountOfAccount(activityValue)

        val accountVO = this.toAccountVo(this)
        val activityVO = this.toActivityOfCredit(activityValue)

        repository.persistNewActivityAndUpdateAmount(accountVO, activityVO)
    }

    fun createNewActivityOfDebitAndUpdateAmountOfAccount(repository: AccountPersistencePort, activityValue: BigDecimal) {
        isNewactivityOfDebitValid(activityValue)
        updateDebitAmountOfAccount(activityValue)

        val accountVO = this.toAccountVo(this)
        val activityVO = this.toActivityOfDebit(activityValue)

        repository.persistNewActivityAndUpdateAmount(accountVO, activityVO)
    }

    private fun updateCreditAmountOfAccount(activityValue: BigDecimal) {
        amount = amount.add(activityValue)
    }

    private fun updateDebitAmountOfAccount(activityValue: BigDecimal) {
        amount = amount.subtract(activityValue)
    }

    private fun isNewAccountValid() {
        this.validator.validateRulesOfPerson()
        this.validator.validateRulesOfAmount()
    }

    private fun isNewActivityOfCreditValid(activityValue: BigDecimal) {
        validator.validateRulesOfValueToNewactivity(activityValue)
    }

    private fun isNewactivityOfDebitValid(activityValue: BigDecimal) {
        validator.validateRulesOfValueToNewactivity(activityValue)
        validator.validateRulesOfSufficientAmountToNewActivityOfDebit(activityValue)
    }

    private fun toAccountVo(account: Account): AccountVO {
        return AccountVO(
                personName = account.personName,
                number = account.number,
                amount = account.amount,
                activities = mutableListOf()
        )
    }

    private fun toActivityOfDebit(activityValue: BigDecimal): ActivityVO {
        return ActivityVO(activityValue, ActivityType.DEBIT.name)
    }

    private fun toActivityOfCredit(activityValue: BigDecimal): ActivityVO {
        return ActivityVO(activityValue, ActivityType.CREDIT.name)
    }

    private fun generateNewAccountNumber(repository: AccountPersistencePort): String {
        val nextNumber: Int = repository.generateNextAccountNumber()
        val lengthNumber = 10
        return String.format("%1$" + lengthNumber + "s", nextNumber).replace(' ', '0')
    }


}