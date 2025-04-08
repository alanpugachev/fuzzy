package com.alanpugachev

import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.server.http.content.*
import io.ktor.server.routing.*
import kotlinx.html.*

fun Application.configureRouting() {
    routing {
        staticResources("/static", "static")

        get("/") {
            call.respondHtml {
                head {
                    title { +"Home" }
                    link(href = "/static/styles.css", rel = "stylesheet")
                }
                body {
                    div(classes = "container") {
                        div(classes = "navbar") {
                            a(href = "/") {
                                button(classes = "nav-button") {
                                    +"Home"
                                }
                            }

                            a(href = "/about") {
                                button(classes = "nav-button") {
                                    +"About"
                                }
                            }

                            a(href = "/results") {
                                button(classes = "nav-button") {
                                    +"Results"
                                }
                            }
                        }

                        h1 { +"Mini-mult" }
                    }
                }
            }
        }
    }
}
