package com.example.demo.boundaries.account.application

import com.example.demo.boundaries.account.domain.AccountVO
import com.example.demo.boundaries.account.presentation.AccountApiRequest
import com.example.demo.boundaries.account.presentation.MovementApiRequest

interface AccountUseCase {

    fun createNewAccount(request: AccountApiRequest): AccountVO
    fun createNewMovementOfCredit(accountNumber: String, request: MovementApiRequest): AccountVO
    fun createNewMovementOfDebit(accountNumber: String, request: MovementApiRequest): AccountVO
    fun retrieveAccountByNumber(accountNumber: String): AccountVO
    fun retrieveAllAccount(): List<AccountVO>

}