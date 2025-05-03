package com.alanpugachev

import com.alanpugachev.apps.launchKafkaConsumer
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.html.*
import io.ktor.server.netty.*
import io.ktor.server.routing.*
import kotlinx.html.b
import kotlinx.html.body
import org.jetbrains.exposed.sql.transactions.transaction

fun main(args: Array<String>) {
    DatabaseFactory.init()

    embeddedServer(
        Netty,
        port = 8080,
        host = "0.0.0.0",
        module = Application::module
    )
        .start(true)
        .monitor
        .subscribe(ApplicationStopping) {
            DatabaseFactory.close()
        }
}

fun Application.module() {
    configureRouting()
    launchKafkaConsumer()

    routing {
        get("/health") {
            lateinit var dbStatus: String

            transaction {
                dbStatus = try {
                    exec("SELECT 1") {}
                    "OK"
                } catch (e: Exception) {
                    "ERROR: ${e.message}"
                }
            }

            call.respondHtml {
                body {
                    b {
                        +"db status: $dbStatus"
                    }
                }
            }
        }
    }
}
