package com.alanpugachev.repos

import com.alanpugachev.dba.SurveyResultsTable
import com.alanpugachev.entities.SurveyResult
import kotlinx.datetime.LocalDateTime
import org.jetbrains.exposed.sql.SortOrder
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

    fun getAll(): Result<List<SurveyResultReadDTO>> = runCatching {
        transaction {
            SurveyResultsTable
                .selectAll()
                .orderBy(SurveyResultsTable.createdAt, SortOrder.DESC)
                .map { row ->
                    SurveyResultReadDTO(
                        id = row[SurveyResultsTable.id].value,
                        results = row[SurveyResultsTable.results],
                        createdAt = row[SurveyResultsTable.createdAt],
                        updatedAt = row[SurveyResultsTable.updatedAt],
                        deletedAt = row[SurveyResultsTable.deletedAt],
                    )

                }
        }
    }

    fun getAllRaw(): Result<List<SurveyResult>> = runCatching {
        transaction {
            SurveyResultsTable
                .selectAll()
                .map {
                    it[SurveyResultsTable.results]
                }
        }
    }
}

data class SurveyResultCreateDTO(
    val result: SurveyResult,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)

data class SurveyResultReadDTO(
    val id: Int,
    val results: SurveyResult,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val deletedAt: LocalDateTime?
)