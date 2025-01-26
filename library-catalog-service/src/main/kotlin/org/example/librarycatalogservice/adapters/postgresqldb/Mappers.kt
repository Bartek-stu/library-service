package org.example.librarycatalogservice.adapters.postgresqldb

import org.example.librarycatalogservice.domain.model.Book

internal fun Book.toEntity(): BookEntity = BookEntity(id, title, author)