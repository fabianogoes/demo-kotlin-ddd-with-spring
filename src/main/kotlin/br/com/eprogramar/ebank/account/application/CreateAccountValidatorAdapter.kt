package br.com.eprogramar.ebank.account.application

import br.com.eprogramar.ebank.account.application.port.`in`.CreateAccountValidatorPort
import org.springframework.util.Assert
import javax.inject.Named

@Named
class CreateAccountValidatorAdapter: CreateAccountValidatorPort {
    override fun validationToNewAccount(personName: String) {
        validateRulesOfPerson(personName)
    }

    private fun validateRulesOfPerson(personName: String) {
        Assert.notNull(personName, "[Person] should not be Null!")
        Assert.hasText(personName, "[Person] should not be Black!")
        Assert.isTrue(personName.length >= 5, "[Person] should has greater than 4 words!")
    }
}