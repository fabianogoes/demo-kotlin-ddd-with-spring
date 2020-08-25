package br.com.eprogramar.ebank.account.domain

internal class AccountNotFoundException(accountNumber: Int) :
    RuntimeException("Account[$accountNumber] Not Found to Deposit")

internal class CreateAccountException : RuntimeException("Create Account Error")

internal class SendDepositAccountNotFoundException(accountNumber: Int) :
        RuntimeException("Account[$accountNumber] Not Found to Deposit")

internal class SendWithDrawAccountNotFoundException(accountNumber: Int) :
        RuntimeException("Account[$accountNumber] Not Found to WithDraw")