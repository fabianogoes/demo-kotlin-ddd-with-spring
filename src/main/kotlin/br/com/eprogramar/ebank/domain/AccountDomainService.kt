package br.com.eprogramar.ebank.domain

import br.com.eprogramar.ebank.crosscutting.ActivityType
import br.com.eprogramar.ebank.domain.vo.AccountVO
import br.com.eprogramar.ebank.presentation.request.AccountApiRequest
import br.com.eprogramar.ebank.presentation.request.ActivityApiRequest

interface AccountDomainService {

    fun createNewAccount(request: AccountApiRequest): AccountVO
    fun createNewActivity(accountNumber: String, activityType: ActivityType, request: ActivityApiRequest): AccountVO
    fun retrieveAccountByNumber(accountNumber: String): AccountVO
    fun retrieveAllAccount(): List<AccountVO>

}