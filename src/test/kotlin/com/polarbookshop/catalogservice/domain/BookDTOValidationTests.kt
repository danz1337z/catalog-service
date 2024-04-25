package com.polarbookshop.catalogservice.domain

import jakarta.validation.ConstraintViolation
import jakarta.validation.Validation
import jakarta.validation.Validator
import jakarta.validation.ValidatorFactory
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test

class BookDTOValidationTests {

    companion object {
        private lateinit var validator: Validator

        @BeforeAll
        @JvmStatic
        fun setUp() {
            val factory: ValidatorFactory = Validation.buildDefaultValidatorFactory()
            validator = factory.validator
        }
    }

    @Test
    fun `when all fields correct then validation succeeds`() {
        val bookDTO = BookUpdateCreateDTo("1234567890", "Title", "Author", 9.90)
        val violations: Set<ConstraintViolation<BookUpdateCreateDTo>> = validator.validate(bookDTO)
        assertTrue(violations.isEmpty())
    }

    @Test
    fun `when ISBN defined but incorrect then validation fails`() {
        val bookDTO = BookUpdateCreateDTo("a234567890", "Title", "Author", 9.90)
        val violations: Set<ConstraintViolation<BookUpdateCreateDTo>> = validator.validate(bookDTO)
        assertEquals(1, violations.size)
        assertEquals("The ISBN format must be valid.", violations.first().message)
    }
}