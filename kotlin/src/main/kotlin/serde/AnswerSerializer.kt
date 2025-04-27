package com.alanpugachev.serde

import com.alanpugachev.entities.Answer
import kotlinx.serialization.json.Json
import org.apache.kafka.common.serialization.Serializer
import org.slf4j.LoggerFactory

class AnswerSerializer : Serializer<List<Answer>> {
    private val logger = LoggerFactory.getLogger(javaClass)

    override fun serialize(topic: String?, data: List<Answer>?): ByteArray =
        try {
            Json.encodeToString(data).toByteArray()
        } catch (e: Throwable) {
            logger.error(e.message, e)
            byteArrayOf()
        }
}