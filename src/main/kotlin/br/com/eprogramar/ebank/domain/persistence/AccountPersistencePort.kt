package br.com.eprogramar.ebank.domain.persistence

import br.com.eprogramar.ebank.domain.vo.AccountVO
import br.com.eprogramar.ebank.domain.vo.ActivityVO


interface AccountPersistencePort {

    fun persisNewAccount(accountVo: AccountVO)
    fun retrieveAllActivities(number: String): List<ActivityVO>
    fun retrieveAccountByNumber(accountNumber: String): AccountVO
    fun persistNewActivityAndUpdateAmount(accountVO: AccountVO, activityVO: ActivityVO)
    fun retrieveAllAccount(): List<AccountVO>
    fun generateNextAccountNumber(): Int

}
