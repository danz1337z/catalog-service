package com.polarbookshop.catalogservice.domain

import org.springframework.stereotype.Service

@Service
class BookService(
    private val bookRepository: BookRepository
) {
    fun viewBookList(): Iterable<Book> = bookRepository.findAll()

    fun viewBookDetails(isbn: String): Book? = bookRepository.findByIsbn(isbn) ?: throw BookNotFoundException(isbn)

    fun addBookToCatalog(book: Book): Book {
        if (bookRepository.existsByIsbn(book.isbn)) {
            throw BookAlreadyExistsException(book.isbn)
        }
        return bookRepository.save(book)
    }

    fun removeBookFromCatalog(isbn: String) = bookRepository.deleteByIsbn(isbn)

    fun editBookDetails(isbn: String, book: Book): Book {
        val existingBook = bookRepository.findByIsbn(isbn)
        return existingBook?.let {
            val bookToUpdate = it.copy(
                title = book.title,
                author = book.author,
                price = book.price
            )
            bookRepository.save(bookToUpdate)
        } ?: addBookToCatalog(book)
    }
}