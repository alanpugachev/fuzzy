package com.alanpugachev.repos

import com.alanpugachev.dba.SurveyResultsTable
import kotlinx.datetime.LocalDateTime
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

class SurveyResultsRepo {
    fun create(dto: SurveyResultCreateDTO): Result<Int> = runCatching {
        transaction {
            SurveyResultsTable.insert {
                it[results] = dto.result
                it[createdAt] = dto.createdAt
                it[updatedAt] = dto.updatedAt
            } [SurveyResultsTable.id].value
        }
    }

    fun get(id: Int): Result<SurveyResultReadDTO> = runCatching {
        transaction {
            SurveyResultsTable
                .selectAll()
                .where {
                    SurveyResultsTable.id eq id
                }
                .map { row ->
                    SurveyResultReadDTO(
                        id = row[SurveyResultsTable.id].value,
                        results = row[SurveyResultsTable.results],
                        createdAt = row[SurveyResultsTable.createdAt],
                        updatedAt = row[SurveyResultsTable.updatedAt],
                        deletedAt = row[SurveyResultsTable.deletedAt],
                    )
                }
                .firstOrNull()
                ?: throw RuntimeException("Repository returned more than one record")
        }
    }
}

data class SurveyResultCreateDTO(
    val result: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)

data class SurveyResultReadDTO(
    val id: Int,
    val results: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val deletedAt: LocalDateTime?
)