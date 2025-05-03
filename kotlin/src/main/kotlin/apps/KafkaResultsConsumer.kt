package com.alanpugachev.apps

import com.alanpugachev.entities.SurveyResult
import com.alanpugachev.repos.SurveyResultCreateDTO
import com.alanpugachev.repos.SurveyResultsRepo
import io.ktor.server.application.*
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.json.Json
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.common.serialization.StringDeserializer
import java.time.Duration
import java.util.*
import kotlin.concurrent.thread

fun Application.launchKafkaConsumer() {
    val consumer = configureKafkaConsumer()
    consumer.subscribe(listOf("results"))

    thread(isDaemon = true) {
        while (true) {
            val records = consumer.poll(Duration.ofMillis(100))
            for (record in records) {
                processKafkaRecord(record.value())
            }
        }
    }
}

fun configureKafkaConsumer(): KafkaConsumer<String, String> = run {
    val props = Properties().apply {
        put("bootstrap.servers", "localhost:9092")
        put("group.id", "ktor-consumer-group")
        put("key.deserializer", StringDeserializer::class.java)
        put("value.deserializer", StringDeserializer::class.java)
        put("auto.offset.reset", "earliest")
        put("enable.auto.commit", "true")
    }

    KafkaConsumer<String, String>(props)
}

fun processKafkaRecord(message: String) {
    val surveyResultsRepo = SurveyResultsRepo()

    try {
        println("received data = $message")

        val result: SurveyResult = Json.decodeFromString<SurveyResult>(message)

        surveyResultsRepo
            .create(
                SurveyResultCreateDTO(
                    result = result,
                    createdAt = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()),
                    updatedAt = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
                )
            )

    } catch (e: Exception) {
        println("Error processing message: ${e.message}")
    }
}