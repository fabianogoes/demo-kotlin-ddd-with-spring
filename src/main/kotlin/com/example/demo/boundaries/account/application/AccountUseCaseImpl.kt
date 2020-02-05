package com.example.demo.boundaries.account.application

import com.example.demo.boundaries.account.domain.*
import com.example.demo.boundaries.account.infrastructure.AccountRepository
import com.example.demo.boundaries.account.presentation.AccountApiRequest
import com.example.demo.boundaries.account.presentation.MovementApiRequest
import org.springframework.stereotype.Service

@Service
class AccountUseCaseImpl(private val repository: AccountRepository) : AccountUseCase {

    override fun createNewAccount(request: AccountApiRequest): AccountVO {
        return Account(request.person, request.amount).createNewAccount(repository)
    }

    override fun createNewMovementOfCredit(accountNumber: String, request: MovementApiRequest): AccountVO {
        val accountVO = repository.retrieveAccountByNumber(accountNumber)
        Account(accountVO).createNewMovementOfCreditAndUpdateAmountOfAccount(repository, request.movementValue)
        return repository.retrieveAccountByNumber(accountNumber)
    }

    override fun createNewMovementOfDebit(accountNumber: String, request: MovementApiRequest): AccountVO {
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