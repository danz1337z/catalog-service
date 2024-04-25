package com.polarbookshop.catalogservice.domain.models

import org.jetbrains.exposed.dao.id.IntIdTable

object Book : IntIdTable(name = "books") {
    val isbn = text("isbn").uniqueIndex()
    val title = text("title")
    val author = text("author")
    val price = double("price")
    val version = integer("version")
}