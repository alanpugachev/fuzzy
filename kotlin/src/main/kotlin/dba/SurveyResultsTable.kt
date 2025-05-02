package com.alanpugachev.dba

import kotlinx.serialization.json.Json
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.json.jsonb
import org.jetbrains.exposed.sql.kotlin.datetime.timestamp

object SurveyResultsTable : IntIdTable("survey_results") {
    val result = jsonb<Map<String, String>>(
        name = "results",
        serialize = { meta -> Json.encodeToString(meta) },
        deserialize = { meta -> Json.decodeFromString(meta) }
    )
    val createdAt = timestamp(name = "created_at")
    val updatedAt = timestamp(name = "updated_at")
    val deletedAt = timestamp(name = "deleted_at").nullable()
}