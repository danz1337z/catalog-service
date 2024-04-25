package com.polarbookshop.catalogservice.web

import com.polarbookshop.catalogservice.domain.Book
import com.polarbookshop.catalogservice.domain.BookService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/books")
class BookController(private val bookService: BookService) {

    @GetMapping
    fun get(): Iterable<Book> = bookService.viewBookList()

    @GetMapping("/{isbn}")
    fun getByIsbn(@PathVariable(value = "isbn") isbn: String): Book? = bookService.viewBookDetails(isbn)

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun post(@Valid @RequestBody book: Book): Book = bookService.addBookToCatalog(book)

    @DeleteMapping("/{isbn}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable(value = "isbn") isbn: String) = bookService.removeBookFromCatalog(isbn)

    @PutMapping("/{isbn}")
    fun update(@PathVariable(value = "isbn") isbn: String, @Valid @RequestBody book: Book): Book = bookService.editBookDetails(isbn, book)
}