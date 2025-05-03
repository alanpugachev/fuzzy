package com.alanpugachev.serde

import org.apache.kafka.common.serialization.Deserializer
import org.slf4j.LoggerFactory

/* todo refactor later */
class ResultDeserializer : Deserializer<String> {
    private val logger = LoggerFactory.getLogger(javaClass)

    override fun deserialize(topic: String?, data: ByteArray?): String =
        try {
            data?.toString() ?: throw RuntimeException("data is null")
        } catch (e: Throwable) {
            logger.error(e.message, e)
            "null"
        }
}