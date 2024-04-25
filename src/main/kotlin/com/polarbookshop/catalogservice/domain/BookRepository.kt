package com.polarbookshop.catalogservice.domain

interface BookRepository {
    fun findAll(): Iterable<BookDTO>
    fun findByIsbn(isbn: String): BookDTO?
    fun existsByIsbn(isbn: String): Boolean
    fun save(bookDTO: BookUpdateCreateDTo): BookDTO
    fun deleteByIsbn(isbn: String)
}