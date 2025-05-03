package com.alanpugachev.routes

import io.ktor.server.html.*
import io.ktor.server.routing.*
import kotlinx.html.*

fun Route.homeRoute() {
    get("/") {
        call.respondHtml {
            head {
                title { +"Mini-Mult" }
                link(href = "/static/styles.css", rel = "stylesheet")
                link(href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600&display=swap", rel="stylesheet")
            }
            body {
                div(classes = "container") {
                    div(classes = "content-wrapper") {
                        div(classes = "navbar") {
                            a(href = "/") {
                                button(classes = "nav-button") { +"Home" }
                            }
                            a(href = "/about") {
                                button(classes = "nav-button") { +"About" }
                            }
                        }

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
                                div(classes = "use-card") { +"Initial clinical screening" }
                                div(classes = "use-card") { +"Research studies" }
                                div(classes = "use-card") { +"Brief personality assessment" }
                                div(classes = "use-card") { +"Settings requiring rapid evaluation" }
                            }

                            div(classes = "survey-action") {
                                a(href = "/survey") {
                                    button(classes = "survey-button") {
                                        +"Take Survey"
                                    }
                                }
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