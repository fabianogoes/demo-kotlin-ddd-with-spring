package com.example.demo.boundaries.account.domain

import com.example.demo.boundaries.account.presentation.AccountRequest
import com.example.demo.boundaries.account.presentation.MovementRequest

interface AccountService {

    fun createNewAccount(request: AccountRequest): AccountVO
    fun createNewMovementOfCredit(accountNumber: String, request: MovementRequest): AccountVO
    fun createNewMovementOfDebit(accountNumber: String, request: MovementRequest): AccountVO
    fun retrieveAccountByNumber(accountNumber: String): AccountVO
    fun retrieveAllAccount(): List<AccountVO>

}