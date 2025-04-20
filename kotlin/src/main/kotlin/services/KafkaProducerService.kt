package com.alanpugachev.services

import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.clients.producer.RecordMetadata
import org.apache.kafka.common.serialization.StringSerializer
import java.util.*

class KafkaProducerService(
    private val bootstrapServers: String = "192.168.31.125:9092"
) {
    private val producer: KafkaProducer<String, String>

    init {
        val props = Properties().apply {
            put("bootstrap.servers", bootstrapServers)
            put("key.serializer", StringSerializer::class.java)
            put("value.serializer", StringSerializer::class.java)

            /* optional */
            put("acks", "all") /* granted record */
            put("retries", 3)
            put("linger.ms", 5) /* delay */
        }

        producer = KafkaProducer(props)
    }

    /* send message */
    fun sendMessaage(
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
    fun close() { producer.close() }
}