package com.alanpugachev.entities

import kotlinx.serialization.Serializable

@Serializable
class SurveyResult(
    val hysteria: ScaleLevels,
    val hypochondria: ScaleLevels,
    val depression: ScaleLevels,
    val psychopathy: ScaleLevels,
    val paranoia: ScaleLevels,
    val psychasthenia: ScaleLevels,
    val schizophrenia: ScaleLevels,
    val hypomania: ScaleLevels,
    val introversion: ScaleLevels,

    /* Apache Kafka fields */
    val processed_at: String,
    val version: String
)

@Serializable
data class ScaleLevels(
    val low: Double,
    val mid: Double,
    val elevated: Double,
    val high: Double,
    val very_high: Double
)