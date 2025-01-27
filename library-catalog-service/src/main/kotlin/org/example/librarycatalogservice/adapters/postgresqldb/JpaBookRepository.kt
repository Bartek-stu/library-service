package org.example.librarycatalogservice.adapters.postgresqldb

import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface JpaBookRepository: JpaRepository<BookEntity, String> {
    fun findByTitle(title: String): Optional<BookEntity>
}
