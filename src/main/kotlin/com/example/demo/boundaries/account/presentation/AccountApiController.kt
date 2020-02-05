package com.example.demo.boundaries.account.presentation

import com.example.demo.boundaries.account.application.AccountUseCase
import com.example.demo.boundaries.account.domain.AccountVO
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/accounts")
class AccountApiController(private val service: AccountUseCase) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createNewAccount(@RequestBody apiRequest: AccountApiRequest): AccountApiResponse {
        return mapperToAccountResponse(service.createNewAccount(apiRequest))
    }

    @PatchMapping("/{accountNumber}/movement/credit")
    @ResponseStatus(HttpStatus.ACCEPTED)
    fun createMovementOfCredit(@PathVariable accountNumber: String, @RequestBody request: MovementApiRequest): AccountApiResponse {
        return mapperToAccountResponse(service.createNewMovementOfCredit(accountNumber, request))
    }

    @PatchMapping("/{accountNumber}/movement/debit")
    @ResponseStatus(HttpStatus.ACCEPTED)
    fun createMovementOfDebit(@PathVariable accountNumber: String, @RequestBody request: MovementApiRequest): AccountApiResponse {
        return mapperToAccountResponse(service.createNewMovementOfDebit(accountNumber, request))
    }

    @GetMapping("/{accountNumber}")
    fun retrieveAccountByNumber(@PathVariable accountNumber: String): AccountApiResponse {
        return mapperToAccountResponse(service.retrieveAccountByNumber(accountNumber))
    }

    @GetMapping
    fun retrieveAllAccount(): List<AccountApiResponse> {
        return service.retrieveAllAccount().map { ac -> mapperToAccountResponse(ac) }
    }

    fun mapperToAccountResponse(accountVO: AccountVO): AccountApiResponse {
        return AccountApiResponse(
                person = accountVO.personName,
                number = accountVO.number,
                amount = accountVO.amount,
                movements = accountVO.movements.map { MovementApiResponse(value = it.value, type = it.type) }
        )
    }

}