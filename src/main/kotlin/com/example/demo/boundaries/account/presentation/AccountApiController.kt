package com.example.demo.boundaries.account.presentation

import com.example.demo.boundaries.account.crosscutting.MovementType
import com.example.demo.boundaries.account.domain.AccountDomainService
import com.example.demo.boundaries.account.domain.vo.AccountVO
import com.example.demo.boundaries.account.presentation.request.AccountApiRequest
import com.example.demo.boundaries.account.presentation.request.MovementApiRequest
import com.example.demo.boundaries.account.presentation.response.AccountApiResponse
import com.example.demo.boundaries.account.presentation.response.MovementApiResponse
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/accounts")
class AccountApiController(private val service: AccountDomainService) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createNewAccount(@RequestBody apiRequest: AccountApiRequest) =
        mapperToAccountResponse(service.createNewAccount(apiRequest))

    @PatchMapping("/{accountNumber}/movement/{movementType}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    fun createMovementOfCredit(
            @PathVariable accountNumber: String,
            @PathVariable movementType: MovementType,
            @RequestBody request: MovementApiRequest) =
        mapperToAccountResponse(service.createNewMovement(accountNumber, movementType, request))

    @GetMapping("/{accountNumber}")
    fun retrieveAccountByNumber(@PathVariable accountNumber: String) =
        mapperToAccountResponse(service.retrieveAccountByNumber(accountNumber))

    @GetMapping
    fun retrieveAllAccount(): List<AccountApiResponse> =
        service.retrieveAllAccount().map { ac -> mapperToAccountResponse(ac) }

    fun mapperToAccountResponse(accountVO: AccountVO) =
        AccountApiResponse(
                person = accountVO.personName,
                number = accountVO.number,
                amount = accountVO.amount,
                movements = accountVO.movements.map { MovementApiResponse(value = it.value, type = it.type) }
        )
}