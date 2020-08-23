package br.com.eprogramar.ebank.presentation

import br.com.eprogramar.ebank.crosscutting.ActivityType
import br.com.eprogramar.ebank.domain.AccountDomainService
import br.com.eprogramar.ebank.domain.vo.AccountVO
import br.com.eprogramar.ebank.presentation.request.AccountApiRequest
import br.com.eprogramar.ebank.presentation.request.ActivityApiRequest
import br.com.eprogramar.ebank.presentation.response.AccountApiResponse
import br.com.eprogramar.ebank.presentation.response.ActivityApiResponse
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/accounts")
class AccountApiController(private val service: AccountDomainService) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createNewAccount(@RequestBody apiRequest: AccountApiRequest) =
        mapperToAccountResponse(service.createNewAccount(apiRequest))

    @PatchMapping("/{accountNumber}/activity/{activityType}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    fun createActivityOfCredit(
            @PathVariable accountNumber: String,
            @PathVariable activityType: ActivityType,
            @RequestBody request: ActivityApiRequest) =
        mapperToAccountResponse(service.createNewActivity(accountNumber, activityType, request))

    @GetMapping("/{accountNumber}")
    fun retrieveAccountByNumber(@PathVariable accountNumber: String) =
        mapperToAccountResponse(service.retrieveAccountByNumber(accountNumber))

    @GetMapping
    fun retrieveAllAccounts(): List<AccountApiResponse> =
        service.retrieveAllAccount().map(this::mapperToAccountResponse)

    fun mapperToAccountResponse(accountVO: AccountVO) =
        AccountApiResponse(
                person = accountVO.personName,
                number = accountVO.number,
                amount = accountVO.amount,
                activities = accountVO.activities.map { ActivityApiResponse(value = it.value, type = it.type) }
        )
}