package com.alanpugachev.services

import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.clients.producer.RecordMetadata
import org.apache.kafka.common.serialization.Serializer
import org.apache.kafka.common.serialization.StringSerializer
import java.util.*

class KafkaProducerService<T : Any>(
    private val bootstrapServers: String = "localhost:9092",
    private val serializer: KSerializer<T>
) {
    private val producer: KafkaProducer<String, String>
    private val jsonFormat = Json { prettyPrint = false }

    init {
        val props = Properties().apply {
            put("bootstrap.servers", bootstrapServers)
            put("key.serializer", StringSerializer::class.java)
            put("value.serializer", KotlinxJsonSerializer::class)

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
        value: String
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

    private inner class KotlinxJsonSerializer : Serializer<T> {
        override fun serialize(topic: String, data: T): ByteArray = jsonFormat
            .encodeToString(serializer, data)
            .toByteArray()

        override fun close() {}
        override fun configure(configs: MutableMap<String, *>?, isKey: Boolean) {}
    }
}