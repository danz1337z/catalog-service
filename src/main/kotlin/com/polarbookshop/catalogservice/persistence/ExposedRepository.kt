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
import java.time.LocalDateTime
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
            val existingBook = findByIsbn(bookDTO.isbn)

            if (existingBook != null) {
                Book.update({ Book.isbn eq bookDTO.isbn }) {
                    it[title] = bookDTO.title
                    it[author] = bookDTO.author
                    it[price] = bookDTO.price
                    it[version] = existingBook.version.plus(1)
                    it[lastModifiedDate] = LocalDateTime.now()
                }
            } else {
                Book.insert {
                    it[isbn] = bookDTO.isbn
                    it[title] = bookDTO.title
                    it[author] = bookDTO.author
                    it[price] = bookDTO.price
                }
            }
            findByIsbn(bookDTO.isbn)!!
        }
    }

    override fun deleteByIsbn(isbn: String) {
        transaction(database) {
            Book.deleteWhere { Book.isbn eq isbn }
        }
    }

    override fun deleteAll() {
        transaction(database) {
            Book.deleteAll()
        }
    }

    override fun saveAll(bookDTOs: List<BookUpdateCreateDTo>) {
        transaction(database) {
            Book.batchInsert(bookDTOs) { (isbn, title, author, price ) ->
                this[Book.isbn] = isbn
                this[Book.title] = title
                this[Book.author] = author
                this[Book.price] = price
            }
        }
    }
}