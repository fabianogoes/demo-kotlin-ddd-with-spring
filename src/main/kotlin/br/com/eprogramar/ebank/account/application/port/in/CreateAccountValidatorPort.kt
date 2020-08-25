package br.com.eprogramar.ebank.account.application.port.`in`

interface CreateAccountValidatorPort {
    fun validationToNewAccount(personName: String)
}