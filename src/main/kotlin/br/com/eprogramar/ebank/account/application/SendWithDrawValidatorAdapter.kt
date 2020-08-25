package br.com.eprogramar.ebank.account.application

import br.com.eprogramar.ebank.account.application.port.`in`.SendWithDrawValidatorPort
import br.com.eprogramar.ebank.account.crosscutting.request.SendWithDrawRequest
import org.springframework.util.Assert
import javax.inject.Named

@Named
class SendWithDrawValidatorAdapter: SendWithDrawValidatorPort {
    override fun validateRulesOfValueToWithDraw(sendWithDrawRequest: SendWithDrawRequest) {
        Assert.notNull(sendWithDrawRequest.value, "[Deposit Value] should not be Null!")
        Assert.isTrue(sendWithDrawRequest.value.toDouble().compareTo(0) == 1, "[Deposit Value] should be Greater than ZERO!")
    }
}