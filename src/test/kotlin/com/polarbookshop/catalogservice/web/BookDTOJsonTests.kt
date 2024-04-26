package com.polarbookshop.catalogservice.web

import com.polarbookshop.catalogservice.domain.BookUpdateCreateDTo
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.json.JsonTest
import org.springframework.boot.test.json.JacksonTester

@JsonTest
class BookDTOJsonTests {
    @Autowired
    private lateinit var json: JacksonTester<BookUpdateCreateDTo>

    @Test
    fun `test serialize`() {
        val bookDTO = BookUpdateCreateDTo("1234567890", "Title", "Author", 9.90, "Publisher")
        val jsonContent = json.write(bookDTO)
        assertThat(jsonContent).extractingJsonPathStringValue("@.isbn").isEqualTo(bookDTO.isbn)
        assertThat(jsonContent).extractingJsonPathStringValue("@.title").isEqualTo(bookDTO.title)
        assertThat(jsonContent).extractingJsonPathStringValue("@.author").isEqualTo(bookDTO.author)
        assertThat(jsonContent).extractingJsonPathNumberValue("@.price").isEqualTo(bookDTO.price)
        assertThat(jsonContent).extractingJsonPathStringValue("@.publisher").isEqualTo(bookDTO.publisher)
    }

    @Test
    fun `test deserialize`() {
        val content = """
            {
                "isbn": "1234567890",
                "title": "Title",
                "author": "Author",
                "price": 9.90
                "publisher": "Publisher"
            }
        """.trimIndent()
        assertEquals(BookUpdateCreateDTo("1234567890", "Title", "Author", 9.90, "Publisher"), json.parse(content).`object`)
    }
}