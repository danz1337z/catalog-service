package com.polarbookshop.catalogservice.demo

import com.polarbookshop.catalogservice.domain.BookRepository
import com.polarbookshop.catalogservice.domain.BookUpdateCreateDTo
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.annotation.Profile
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
@Profile("testData")
class BookDataLoader(val bookRepository: BookRepository) {

    @EventListener(ApplicationReadyEvent::class)
    fun loadBookTestData() {
        bookRepository.deleteAll()
        val bookDTO1 = BookUpdateCreateDTo("1234567890", "Northern Lights", "Lyra Silverstar", 9.90, null)
        val bookDTO2 = BookUpdateCreateDTo("1234567891", "Polar Journey", "Iorek Polarson", 12.90, "Polarsophia")
        bookRepository.saveAll(listOf(bookDTO1, bookDTO2))
    }
}