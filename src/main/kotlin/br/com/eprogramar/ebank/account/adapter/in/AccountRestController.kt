package br.com.eprogramar.ebank.account.adapter.`in`

import br.com.eprogramar.ebank.account.application.port.`in`.CreateAccountUseCase
import br.com.eprogramar.ebank.account.application.port.`in`.RetrieveAccountUseCase
import br.com.eprogramar.ebank.account.application.port.`in`.SendDepositUseCase
import br.com.eprogramar.ebank.account.application.port.`in`.SendWithDrawUseCase
import br.com.eprogramar.ebank.account.crosscutting.request.CreateAccountRequest
import br.com.eprogramar.ebank.account.crosscutting.request.SendDepositRequest
import br.com.eprogramar.ebank.account.crosscutting.request.SendWithDrawRequest
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("accounts")
class AccountRestController(
        private val createAccountUseCase: CreateAccountUseCase,
        private val sendDepositUseCase: SendDepositUseCase,
        private val retrieveAccountUseCase: RetrieveAccountUseCase,
        private val sendWithDrawUseCase: SendWithDrawUseCase
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createNewAccount(@RequestBody createAccountRequest: CreateAccountRequest) =
            createAccountUseCase.execute(createAccountRequest)

    @PatchMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    fun sendDeposit(@RequestBody sendDepositRequest: SendDepositRequest) =
            sendDepositUseCase.execute(sendDepositRequest)

    @GetMapping("{accountNumber}")
    fun retrieveAccountByNumber(@PathVariable accountNumber: Int) =
            retrieveAccountUseCase.retrieveOne(accountNumber)

    @GetMapping
    fun retrieveAllAccounts() = retrieveAccountUseCase.retrieveAll()

    @DeleteMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    fun withDraw(@RequestBody sendWithDrawRequest: SendWithDrawRequest) =
            sendWithDrawUseCase.execute(sendWithDrawRequest)
}