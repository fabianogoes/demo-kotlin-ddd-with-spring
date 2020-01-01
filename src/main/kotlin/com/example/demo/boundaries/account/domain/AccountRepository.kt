package com.example.demo.boundaries.account.domain

interface AccountRepository {

    fun persisNewAccount(accountVo: AccountVO)
    fun retrieveAllMovements(number: String): List<MovementVO>
    fun retrieveAccountByNumber(accountNumber: String): AccountVO
    fun persistNewMovementAndUpdateAmount(accountVO: AccountVO, movementVO: MovementVO)
    fun retrieveAllAccount(): List<AccountVO>

}
