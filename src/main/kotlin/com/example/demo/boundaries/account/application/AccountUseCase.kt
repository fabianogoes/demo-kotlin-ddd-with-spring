package com.example.demo.boundaries.account.application

import com.example.demo.boundaries.account.domain.*
import com.example.demo.boundaries.account.presentation.AccountRequest
import com.example.demo.boundaries.account.presentation.MovementRequest
import org.springframework.stereotype.Service

@Service
class AccountUseCase(private val repository: AccountRepository) : AccountService {

    override fun createNewAccount(request: AccountRequest): AccountVO {
        return Account(request.person, request.amount).createNewAccount(repository)
    }

    override fun createNewMovementOfCredit(accountNumber: String, request: MovementRequest): AccountVO {
        val accountVO = repository.retrieveAccountByNumber(accountNumber)
        Account(accountVO).createNewMovementOfCreditAndUpdateAmountOfAccount(repository, request.movementValue)
        return repository.retrieveAccountByNumber(accountNumber)
    }

    override fun createNewMovementOfDebit(accountNumber: String, request: MovementRequest): AccountVO {
        val accountVO = repository.retrieveAccountByNumber(accountNumber)
        Account(accountVO).createNewMovementOfDebitAndUpdateAmountOfAccount(repository, request.movementValue)
        return repository.retrieveAccountByNumber(accountNumber)
    }

    override fun retrieveAccountByNumber(accountNumber: String): AccountVO {
        return repository.retrieveAccountByNumber(accountNumber)
    }

    override fun retrieveAllAccount(): List<AccountVO> {
        return repository.retrieveAllAccount()
    }
}