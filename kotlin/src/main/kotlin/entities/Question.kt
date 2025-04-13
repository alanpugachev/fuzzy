package com.alanpugachev.entities

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.io.File

@Serializable
class Question(
    val id: Int,
    val text: String,
    val category: String,
    val reverse_scored: Boolean
) {
    companion object {
        val questions = Json.decodeFromString<List<Question>>(
            File("kotlin/src/main/resources/questions.json").readText()
        )
    }
}