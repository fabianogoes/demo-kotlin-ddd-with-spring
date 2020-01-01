package com.example.demo.boundaries.account.presentation

import com.example.demo.boundaries.account.domain.AccountRequest
import com.example.demo.boundaries.account.domain.AccountService
import com.example.demo.boundaries.account.domain.AccountVO
import com.example.demo.boundaries.account.domain.MovementRequest
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/accounts")
class AccountResource(private val service: AccountService) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createNewAccount(@RequestBody request: AccountRequest): AccountResponse {
        return mapperToAccountResponse(service.createNewAccount(request))
    }

    @PatchMapping("/{accountNumber}/movement/credit")
    @ResponseStatus(HttpStatus.ACCEPTED)
    fun createMovementOfCredit(@PathVariable accountNumber: String, @RequestBody request: MovementRequest): AccountResponse {
        return mapperToAccountResponse(service.createNewMovementOfCredit(accountNumber, request))
    }

    @PatchMapping("/{accountNumber}/movement/debit")
    @ResponseStatus(HttpStatus.ACCEPTED)
    fun createMovementOfDebit(@PathVariable accountNumber: String, @RequestBody request: MovementRequest): AccountResponse {
        return mapperToAccountResponse(service.createNewMovementOfDebit(accountNumber, request))
    }

    @GetMapping("/{accountNumber}")
    fun retrieveAccountByNumber(@PathVariable accountNumber: String): AccountResponse {
        return mapperToAccountResponse(service.retrieveAccountByNumber(accountNumber))
    }

    @GetMapping
    fun retrieveAllAccount(): List<AccountResponse> {
        return service.retrieveAllAccount().map { ac -> mapperToAccountResponse(ac) }
    }

    fun mapperToAccountResponse(accountVO: AccountVO): AccountResponse {
        return AccountResponse(
                person = accountVO.personName,
                number = accountVO.number,
                amount = accountVO.amount,
                movements = accountVO.movements.map { MovementResponse(value = it.value, type = it.type) }
        )
    }

}