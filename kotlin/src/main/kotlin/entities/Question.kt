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
)

val questions = Json.decodeFromString<List<Question>>(
    File("resources/questions.json").readText()
)