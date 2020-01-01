package com.example.demo.boundaries.account.domain

interface AccountService {

    fun createNewAccount(request: AccountRequest): AccountVO
    fun createNewMovementOfCredit(accountNumber: String, request: MovementRequest): AccountVO
    fun createNewMovementOfDebit(accountNumber: String, request: MovementRequest): AccountVO
    fun retrieveAccountByNumber(accountNumber: String): AccountVO
    fun retrieveAllAccount(): List<AccountVO>

}