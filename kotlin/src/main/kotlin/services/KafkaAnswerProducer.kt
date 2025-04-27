package com.alanpugachev.services

import com.alanpugachev.entities.Answer
import com.alanpugachev.serde.AnswerSerializer
import kotlinx.serialization.json.Json
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.clients.producer.RecordMetadata
import org.apache.kafka.common.serialization.StringSerializer
import java.util.*
import kotlin.collections.List

class KafkaAnswerProducer(
    private val bootstrapServers: String = "localhost:9092"
) {
    private val producer: KafkaProducer<String, List<Answer>>
    private val jsonFormat = Json { prettyPrint = false }

    init {
        val props = Properties().apply {
            put("bootstrap.servers", bootstrapServers)
            put("key.serializer", StringSerializer::class.java)
            put("value.serializer", AnswerSerializer::class.java)

            /* optional */
            put("acks", "all") /* granted record */
            put("retries", 3)
            put("linger.ms", 5) /* delay */
        }

        producer = KafkaProducer(props)
    }

    /* send message */
    fun sendMessage(
        topic: String,
        key: String? = null,
        value: List<Answer>
    ) {
        val record = ProducerRecord(topic, key, value)

        producer.send(record) { metadata: RecordMetadata, exception ->
            exception
                ?.let {
                    println("Sending error: ${it.message}")
                }
                ?: println("Sent: ${metadata.topic()}-${metadata.partition()}@${metadata.offset()}")
        }
    }

    /* close producer */
    fun close() {
        producer.close()
    }
}