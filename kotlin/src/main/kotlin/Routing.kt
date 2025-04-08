package com.alanpugachev

import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.server.routing.*
import kotlinx.html.*

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondHtml {
                head {
                    title { +"Home" }
                }
                body {
                    div(classes = "nav-buttons") {
                        a(href = "/") { +"Home" }

                        a(href = "/about") { +"About" }

                        a(href = "/results") { +"Results" }
                    }
                    h1 { +"Mini-mult" }
                }
            }
        }
    }
}
