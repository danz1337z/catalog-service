package com.polarbookshop.catalogservice.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("integration")
class BookRepositoryExposedTests {
    @Autowired
    private lateinit var repository: BookRepository

    @Test
    fun `find  book by isbn when it exists`() {
        val bookIsbn = "1234567890"
        val book = BookUpdateCreateDTo(bookIsbn, "Title", "Author", 12.90, "Publisher")
        repository.save(book)
        val actualBook = repository.findByIsbn(bookIsbn)

        assertThat(actualBook).isNotNull
        assertThat(actualBook!!.isbn == bookIsbn)
    }
}