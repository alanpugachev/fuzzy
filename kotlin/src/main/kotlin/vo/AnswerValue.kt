package com.alanpugachev.vo

import kotlinx.serialization.Serializable

@JvmInline
@Serializable
value class AnswerValue(val value: Int) {
    init {
        if (value !in 1..5) throw RuntimeException("Answer value out of bounds")
    }
}