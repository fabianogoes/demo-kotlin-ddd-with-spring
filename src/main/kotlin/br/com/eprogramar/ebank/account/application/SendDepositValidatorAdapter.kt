package br.com.eprogramar.ebank.account.application

import br.com.eprogramar.ebank.account.application.port.`in`.SendDepositValidatorPort
import br.com.eprogramar.ebank.account.crosscutting.request.SendDepositRequest
import org.springframework.util.Assert
import java.math.BigDecimal
import javax.inject.Named

@Named
class SendDepositValidatorAdapter: SendDepositValidatorPort {
    override fun validateRulesOfValueToDeposit(sendDepositRequest: SendDepositRequest) {
        Assert.notNull(sendDepositRequest.value, "[Deposit Value] should not be Null!")
        Assert.isTrue(sendDepositRequest.value.toDouble().compareTo(0) == 1, "[Deposit Value] should be Greater than ZERO!")
    }
}