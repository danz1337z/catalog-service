package com.dza.catalogservice.domain

class BookAlreadyExistsException(isbn: String) : RuntimeException("The book with ISBN: $isbn already exists")