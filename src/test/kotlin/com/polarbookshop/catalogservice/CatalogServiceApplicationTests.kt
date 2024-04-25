package com.polarbookshop.catalogservice

import com.polarbookshop.catalogservice.domain.Book
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.reactive.server.WebTestClient

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CatalogServiceApplicationTests {

	@Autowired
	private lateinit var webTestClient: WebTestClient

	@Test
	fun `when post request then book created`() {
		val expectedBook = Book("1231231231", "Title", "Author", 9.90)
		webTestClient.post()
			.uri("/books")
			.bodyValue(expectedBook)
			.exchange()
			.expectStatus().isCreated()
			.expectBody(Book::class.java).value { actualBook ->
				assertNotNull(actualBook)
				assertEquals(expectedBook.isbn, actualBook.isbn)
			}
	}

	@Test
	fun contextLoads() {
	}

}