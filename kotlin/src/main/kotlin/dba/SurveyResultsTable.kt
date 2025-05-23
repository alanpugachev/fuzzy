package com.alanpugachev.dba

import com.alanpugachev.entities.SurveyResult
import kotlinx.serialization.json.Json
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.json.jsonb
import org.jetbrains.exposed.sql.kotlin.datetime.datetime

object SurveyResultsTable : IntIdTable("survey_results") {
    val results = jsonb<SurveyResult>(
        name = "result",
        jsonConfig = Json { prettyPrint = true }
    )
    val createdAt = datetime(name = "created_at")
    val updatedAt = datetime(name = "updated_at")
    val deletedAt = datetime(name = "deleted_at").nullable()
}