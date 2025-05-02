package com.alanpugachev.services

import com.alanpugachev.serde.ResultDeserializer
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.common.serialization.StringDeserializer
import java.time.Duration
import java.util.*

@Deprecated("Don't use this")
class KafkaResultsConsumer(
    private val bootstrapServers: String = "localhost:9092"
) {
    init {
        val consumer: KafkaConsumer<String, String>

        val props = Properties().apply {
            put("bootstrap.servers", bootstrapServers)
            put("key.deserializer", StringDeserializer::class.java)
            put("value.deserializer", ResultDeserializer::class.java)
            put("group.id", "someGroup")
        }

        consumer = KafkaConsumer(props)

        consumer.use {
            consumer.subscribe(listOf("results"))

            while (true) {
                consumer
                    .poll(Duration.ofMillis(50))
                    .forEach { record ->
                        println(
                            "Received record with key ${record.key()} " +
                                    "and value ${record.value()} from topic ${record.topic()} and partition ${record.partition()}"
                        )
                    }
            }
        }
    }
}