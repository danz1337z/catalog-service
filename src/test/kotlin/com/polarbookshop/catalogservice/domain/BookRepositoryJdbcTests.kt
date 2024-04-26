package com.polarbookshop.catalogservice.domain

/*
import com.polarbookshop.catalogservice.config.DataConfig
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.context.annotation.Import
import org.springframework.data.jdbc.core.JdbcAggregateTemplate
import org.springframework.test.context.ActiveProfiles

@DataJdbcTest
@Import(DataConfig::class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("integration")
class BookRepositoryJdbcTests {
    @Autowired
    private lateinit var repository: BookRepository
    @Autowired
    private lateinit var jdbcAggregateTemplate: JdbcAggregateTemplate

    @Test
    fun `find  book by isbn when it exists`() {
        val bookIsbn = "1234567890"
        val book = BookUpdateCreateDTo(bookIsbn, "Title", "Author", 12.90, null)
        jdbcAggregateTemplate.insert(book)
        val actualBook = repository.findByIsbn(bookIsbn)

        assertThat(actualBook).isNotNull
        assertThat(actualBook!!.isbn == bookIsbn)
    }
}
*/
