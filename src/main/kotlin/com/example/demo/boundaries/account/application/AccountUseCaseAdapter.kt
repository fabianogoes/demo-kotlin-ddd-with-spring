package com.example.demo.boundaries.account.application

import com.example.demo.boundaries.account.crosscutting.MovementType
import com.example.demo.boundaries.account.domain.Account
import com.example.demo.boundaries.account.domain.AccountDomainService
import com.example.demo.boundaries.account.domain.persistence.AccountPersistencePort
import com.example.demo.boundaries.account.presentation.request.AccountApiRequest
import com.example.demo.boundaries.account.presentation.request.MovementApiRequest
import javax.inject.Named

@Named
class AccountUseCaseAdapter(private val repository: AccountPersistencePort) : AccountDomainService {

    override fun createNewAccount(request: AccountApiRequest) =
            Account(request.person, request.amount).createNewAccount(repository)

    override fun createNewMovement(
            accountNumber: String,
            movementType: MovementType,
            request: MovementApiRequest) = when(movementType) {

        MovementType.CREDIT -> repository.retrieveAccountByNumber(accountNumber)
                .let { Account(it).createNewMovementOfCreditAndUpdateAmountOfAccount(repository, request.movementValue) }
                .let { repository.retrieveAccountByNumber(accountNumber) }

        MovementType.DEBIT -> repository.retrieveAccountByNumber(accountNumber)
                .let { Account(it).createNewMovementOfDebitAndUpdateAmountOfAccount(repository, request.movementValue) }
                .let { repository.retrieveAccountByNumber(accountNumber) }
    }

    override fun retrieveAccountByNumber(accountNumber: String) = repository.retrieveAccountByNumber(accountNumber)

    override fun retrieveAllAccount() = repository.retrieveAllAccount()
}