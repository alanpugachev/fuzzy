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
                    title { +"Mini-Mult" }
                    link(href = "/static/styles.css", rel = "stylesheet")
                }
                body {
                    div(classes = "container") {
                        div(classes = "navbar") {
                            a(href = "/") { button(classes = "nav-button") { +"Home" } }
                            a(href = "/about") { button(classes = "nav-button") { +"About" } }
                            a(href = "/results") { button(classes = "nav-button") { +"Results" } }
                        }

                        /* todo Make the same width as navbar */
                        main(classes = "content-box") {
                            h1 { +"Mini-Mult Assessment" }

                            div(classes = "highlight-box") {
                                p {
                                    +"""A shortened 71-item version of the MMPI that maintains clinical validity 
                                        while reducing administration time by 80% compared to the full 567-item test.""".trimMargin()
                                }
                            }

                            h2 { +"Key Characteristics" }
                            ul(classes = "feature-list") {
                                li { strong { +"71 items (original MMPI has 567)" } }
                                li { strong { +"20-30 minutes completion time" } }
                                li { strong { +"10 clinical scales matching the full MMPI" } }
                                li { strong { +"3 validity scales (L, F, K) to detect response biases" } }
                                li { strong { +"Same scoring and interpretation as standard MMPI" } }
                            }

                            h2 { +"Common Uses" }
                            div(classes = "uses-grid") {
                                div(classes = "use-card") { +"✓ Initial clinical screening" }
                                div(classes = "use-card") { +"✓ Research studies" }
                                div(classes = "use-card") { +"✓ Brief personality assessment" }
                                div(classes = "use-card") { +"✓ Settings requiring rapid evaluation" }
                            }

                            p(classes = "disclaimer") {
                                +"Note: While efficient, the Mini-Mult may miss subtle nuances detected by the full MMPI."
                            }
                        }
                    }
                }
            }
        }
    }
}
