package com.example.demo.boundaries.account.domain

import com.example.demo.boundaries.account.crosscutting.MovementType
import com.example.demo.boundaries.account.domain.vo.AccountVO
import com.example.demo.boundaries.account.presentation.request.AccountApiRequest
import com.example.demo.boundaries.account.presentation.request.MovementApiRequest

interface AccountDomainService {

    fun createNewAccount(request: AccountApiRequest): AccountVO
    fun createNewMovement(accountNumber: String, movementType: MovementType, request: MovementApiRequest): AccountVO
    fun retrieveAccountByNumber(accountNumber: String): AccountVO
    fun retrieveAllAccount(): List<AccountVO>

}