package com.alanpugachev.entities

import kotlinx.serialization.Serializable

@Serializable
class Result(
    val id: Int,
    val results: String,
)