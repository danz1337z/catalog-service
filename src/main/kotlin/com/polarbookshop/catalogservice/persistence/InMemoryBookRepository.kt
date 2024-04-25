package com.polarbookshop.catalogservice.persistence
/*
import com.polarbookshop.catalogservice.domain.BookDTO
import com.polarbookshop.catalogservice.domain.BookRepository
import com.polarbookshop.catalogservice.domain.BookUpdateCreateDTo
import org.springframework.stereotype.Repository
import java.util.concurrent.ConcurrentHashMap

@Repository
class InMemoryBookRepository : BookRepository {
    companion object {
        private val books: MutableMap<String, BookDTO> = ConcurrentHashMap()
    }

    override fun findAll(): Iterable<BookDTO> = books.values

    override fun findByIsbn(isbn: String): BookDTO? {
        return if (existsByIsbn(isbn)) books[isbn] else null
    }

    override fun existsByIsbn(isbn: String): Boolean = books.containsKey(isbn)

    override fun save(bookDTO: BookUpdateCreateDTo): BookDTO {
        books[bookDTO.isbn] = bookDTO
        return bookDTO
    }

    override fun deleteByIsbn(isbn: String) {
        books.remove(isbn)
    }
}*/
