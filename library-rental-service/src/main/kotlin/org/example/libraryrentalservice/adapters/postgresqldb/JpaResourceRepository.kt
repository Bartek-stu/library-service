package org.example.libraryrentalservice.adapters.postgresqldb

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface JpaResourceRepository : JpaRepository<ResourceEntity, String> {
}