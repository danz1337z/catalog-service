package com.polarbookshop.catalogservice.domain.models

import org.jetbrains.exposed.dao.id.IntIdTable

object Book : IntIdTable(name = "book") {
    val isbn = varchar("isbn", 255).uniqueIndex()
    val title = varchar("title", 255)
    val author = varchar("author", 255)
    val price = double("price")
    val version = integer("version").autoIncrement().default(0)
}