package com.alanpugachev.entities

import com.alanpugachev.vo.AnswerValue
import kotlinx.serialization.Serializable

@Serializable
class Answer(
    val id: String,
    val value: AnswerValue
)