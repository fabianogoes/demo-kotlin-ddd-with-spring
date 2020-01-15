package com.example.demo.boundaries.account.domain

interface AccountRepositoryPort {

    fun persisNewAccount(accountVo: AccountVO)
    fun retrieveAllMovements(number: String): List<MovementVO>
    fun retrieveAccountByNumber(accountNumber: String): AccountVO
    fun persistNewMovementAndUpdateAmount(accountVO: AccountVO, movementVO: MovementVO)
    fun retrieveAllAccount(): List<AccountVO>
    fun generateNextAccountNumber(): Int

}
