package com.polarbookshop.catalogservice.persistence

import com.polarbookshop.catalogservice.domain.BookDTO
import com.polarbookshop.catalogservice.domain.BookRepository
import com.polarbookshop.catalogservice.domain.BookUpdateCreateDTo
import com.polarbookshop.catalogservice.domain.models.Book
import jakarta.annotation.PostConstruct
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.upsert
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
        transaction(database) {
            Book.upsert {
                it[isbn] = bookDTO.isbn
                it[title] = bookDTO.title
                it[author] = bookDTO.author
                it[price] = bookDTO.price
            }
        }
        return findByIsbn(bookDTO.isbn)!!
    }

    override fun deleteByIsbn(isbn: String) {
        transaction(database) {
            Book.deleteWhere { Book.isbn eq isbn }
        }
    }
}