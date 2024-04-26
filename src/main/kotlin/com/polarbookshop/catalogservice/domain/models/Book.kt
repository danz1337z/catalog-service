package com.polarbookshop.catalogservice.domain.models

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.javatime.CurrentDateTime
import org.jetbrains.exposed.sql.javatime.datetime

object Book : IntIdTable(name = "book") {
    val isbn = varchar("isbn", 255).uniqueIndex()
    val title = varchar("title", 255)
    val author = varchar("author", 255)
    val price = double("price")
    val createdDate = datetime("created_time").defaultExpression(CurrentDateTime)
    val lastModifiedDate = datetime("last_modified_time").defaultExpression(CurrentDateTime)
    val version = integer("version").autoIncrement().default(0)
}