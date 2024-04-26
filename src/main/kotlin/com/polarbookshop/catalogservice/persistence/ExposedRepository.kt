package com.polarbookshop.catalogservice.persistence

import com.polarbookshop.catalogservice.domain.BookDTO
import com.polarbookshop.catalogservice.domain.BookRepository
import com.polarbookshop.catalogservice.domain.BookUpdateCreateDTo
import com.polarbookshop.catalogservice.domain.models.Book
import jakarta.annotation.PostConstruct
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Service
import javax.sql.DataSource

@Service
class ExposedRepository : BookRepository {

    private val log = LoggerFactory.getLogger(this::class.java)

    @Autowired
    private lateinit var applicationContext: ApplicationContext

    private lateinit var database: Database

    @PostConstruct
    private fun initialise() {
        log.info("Initializing database persistence...")
        database = Database.connect(applicationContext.getBean("dataSource") as DataSource)
    }

    override fun findAll(): Iterable<BookDTO> = transaction(database) {
        Book.selectAll().map { BookDTO(it) }
    }

    override fun findByIsbn(isbn: String): BookDTO? = transaction(database) {
        Book.selectAll()
            .where { Book.isbn eq isbn }
            .map { BookDTO(it) }
            .singleOrNull()
    }

    override fun existsByIsbn(isbn: String): Boolean = transaction(database) {
        Book.selectAll()
            .where { Book.isbn eq isbn }
            .count() > 0
    }

    override fun save(bookDTO: BookUpdateCreateDTo): BookDTO {
       return transaction(database) {
           val book = BookDTO(0, bookDTO.isbn, bookDTO.title, bookDTO.author, bookDTO.price, 0)
           val existingBook = findByIsbn(book.isbn)

           if (existingBook != null) {
               Book.update({Book.isbn eq book.isbn}) {
                   it[title] = book.title
                   it[author] = book.author
                   it[price] = book.price
                   it[version] = existingBook.version.plus(1)
               }
           } else {
               Book.insert {
                   it[isbn] = book.isbn
                   it[title] = book.title
                   it[author] = book.author
                   it[price] = book.price
               }
           }
           findByIsbn(book.isbn)!!
       }
    }

    override fun deleteByIsbn(isbn: String) {
        transaction(database) {
            Book.deleteWhere { Book.isbn eq isbn }
        }
    }
}