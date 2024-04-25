package com.polarbookshop.catalogservice.web

import com.polarbookshop.catalogservice.domain.BookDTO
import com.polarbookshop.catalogservice.domain.BookService
import com.polarbookshop.catalogservice.domain.BookUpdateCreateDTo
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/books")
class BookController(private val bookService: BookService) {

    @GetMapping
    fun get(): Iterable<BookDTO> = bookService.viewBookList()

    @GetMapping("/{isbn}")
    fun getByIsbn(@PathVariable(value = "isbn") isbn: String): BookDTO? = bookService.viewBookDetails(isbn)

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun post(@Valid @RequestBody bookDTO: BookUpdateCreateDTo): BookDTO = bookService.addBookToCatalog(bookDTO)

    @DeleteMapping("/{isbn}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable(value = "isbn") isbn: String) = bookService.removeBookFromCatalog(isbn)

    @PutMapping("/{isbn}")
    fun update(@PathVariable(value = "isbn") isbn: String, @Valid @RequestBody bookDTO: BookUpdateCreateDTo): BookDTO = bookService.editBookDetails(isbn, bookDTO)
}