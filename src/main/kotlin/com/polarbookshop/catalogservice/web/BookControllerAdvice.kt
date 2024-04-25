package com.polarbookshop.catalogservice.web

import com.polarbookshop.catalogservice.domain.BookAlreadyExistsException
import com.polarbookshop.catalogservice.domain.BookNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class BookControllerAdvice {

    @ExceptionHandler(BookNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun bookNotFoundHandler(exception: BookNotFoundException): String = exception.message ?: "The book was not found."

    @ExceptionHandler(BookAlreadyExistsException::class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    fun bookAlreadyExistsHandler(exception: BookAlreadyExistsException): String = exception.message ?: "The book already exists."

    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleValidationExceptions(exception: MethodArgumentNotValidException): Map<String, String> {
        val errors: MutableMap<String,String> = mutableMapOf()
        exception.bindingResult.allErrors.forEach { error ->
            val fieldName = (error as FieldError).field
            val errorMessage = error.defaultMessage
            errorMessage?.let { errors[fieldName] = it }
        }
        return errors
    }
}