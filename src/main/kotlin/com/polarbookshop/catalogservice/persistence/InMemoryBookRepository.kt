package com.polarbookshop.catalogservice.persistence

import com.polarbookshop.catalogservice.domain.Book
import com.polarbookshop.catalogservice.domain.BookRepository
import org.springframework.stereotype.Repository
import java.util.concurrent.ConcurrentHashMap

@Repository
class InMemoryBookRepository : BookRepository {
    companion object {
        private val books: MutableMap<String, Book> = ConcurrentHashMap()
    }

    override fun findAll(): Iterable<Book> = books.values

    override fun findByIsbn(isbn: String): Book? {
        return if (existsByIsbn(isbn)) books[isbn] else null
    }

    override fun existsByIsbn(isbn: String): Boolean = books.containsKey(isbn)

    override fun save(book: Book): Book {
        books[book.isbn] = book
        return book
    }

    override fun deleteByIsbn(isbn: String) {
        books.remove(isbn)
    }
}