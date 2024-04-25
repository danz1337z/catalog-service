package com.polarbookshop.catalogservice.domain

import org.springframework.stereotype.Service

@Service
class BookService(
    private val bookRepository: BookRepository
) {
    fun viewBookList(): Iterable<BookDTO> = bookRepository.findAll()

    fun viewBookDetails(isbn: String): BookDTO? = bookRepository.findByIsbn(isbn) ?: throw BookNotFoundException(isbn)

    fun addBookToCatalog(bookDTO: BookUpdateCreateDTo): BookDTO {
        if (bookRepository.existsByIsbn(bookDTO.isbn)) {
            throw BookAlreadyExistsException(bookDTO.isbn)
        }
        return bookRepository.save(bookDTO)
    }

    fun removeBookFromCatalog(isbn: String) = bookRepository.deleteByIsbn(isbn)

    fun editBookDetails(isbn: String, bookDTO: BookUpdateCreateDTo): BookDTO {
        val existingBook = bookRepository.findByIsbn(isbn)
        return existingBook?.let {
            val bookToUpdate = BookUpdateCreateDTo(
                isbn = isbn,
                title = bookDTO.title,
                author = bookDTO.author,
                price = bookDTO.price,
            )
            bookRepository.save(bookToUpdate)
        } ?: addBookToCatalog(bookDTO)
    }
}