package com.polarbookshop.catalogservice.domain

import com.polarbookshop.catalogservice.domain.models.Book
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Positive
import org.jetbrains.exposed.sql.ResultRow
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.annotation.Version
import java.time.LocalDateTime

data class BookDTO(

    @Id
    val id: Int,
    val isbn: String,
    val title: String,
    val author: String,
    val price: Double,
    val publisher: String?,
    @CreatedDate
    val createdDate: LocalDateTime,
    @LastModifiedDate
    val lastModifiedDate: LocalDateTime,
    @Version
    val version: Int,
) {
    constructor(it: ResultRow) : this(
        it[Book.id].value,
        it[Book.isbn],
        it[Book.title],
        it[Book.author],
        it[Book.price],
        it[Book.publisher],
        it[Book.createdDate],
        it[Book.lastModifiedDate],
        it[Book.version],
    )
}

data class BookUpdateCreateDTo(
    @get:NotBlank(message = "The book ISBN must be defined.")
    @get:Pattern(
        regexp = "^([0-9]{10}|[0-9]{13})$",
        message = "The ISBN format must be valid.",
    )
    val isbn: String,
    @get:NotBlank(message = "The book title must be defined")
    val title: String,
    @get:NotBlank(message = "The book author must be defined")
    val author: String,
    @get:NotNull(message = "The book price must be defined")
    @get:Positive(message = "The book price must be greater than zero")
    val price: Double,
    val publisher: String?,
)