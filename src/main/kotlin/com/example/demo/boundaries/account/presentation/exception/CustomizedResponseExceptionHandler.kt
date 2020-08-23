package com.example.demo.boundaries.account.presentation.exception

import com.example.demo.boundaries.account.domain.exception.AccountNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.util.*

@ControllerAdvice
internal class CustomizedResponseExceptionHandler : ResponseEntityExceptionHandler() {

    @ExceptionHandler(AccountNotFoundException::class)
    fun handleNotFoundException(ex: Throwable, request: WebRequest): ResponseEntity<Any> {
        val errorDetails = ErrorDetails(Date(), ex.message, request.getDescription(Constants.INCLUDE_CLIENT_INFO))
        return ResponseEntity(errorDetails, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleNotFoundException(ex: IllegalArgumentException, request: WebRequest): ResponseEntity<Any> {
        val errorDetails = ErrorDetails(Date(), ex.message, request.getDescription(Constants.INCLUDE_CLIENT_INFO))
        return ResponseEntity(errorDetails, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(RuntimeException::class)
    fun handleNotFoundException(ex: RuntimeException, request: WebRequest): ResponseEntity<Any> {
        val errorDetails = ErrorDetails(Date(), ex.message, request.getDescription(Constants.INCLUDE_CLIENT_INFO))
        return ResponseEntity(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR)
    }

    class ErrorDetails(val date: Date, val message: String?, val description: String)

    object Constants {
        const val INCLUDE_CLIENT_INFO = false
    }
}