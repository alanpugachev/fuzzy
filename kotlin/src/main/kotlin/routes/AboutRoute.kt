package routes

import io.ktor.server.html.*
import io.ktor.server.routing.*
import kotlinx.html.*

fun Route.aboutRoute() {
    get("/about") {
        call.respondHtml {
            head {
                title { +"About Mini-Mult" }
                link(href = "/static/styles.css", rel = "stylesheet")
                link(href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600&display=swap", rel="stylesheet")
                style {
                    +"""
                    .creator-section {
                        margin-top: 40px;
                        padding-top: 20px;
                        border-top: 1px solid #444;
                    }
                    .thesis-badge {
                        background-color: #4527A0;
                        color: white;
                        padding: 5px 10px;
                        border-radius: 15px;
                        font-size: 0.9em;
                        display: inline-block;
                        margin-bottom: 15px;
                    }
                    """
                }
            }
            body {
                div(classes = "container") {
                    div(classes = "content-wrapper") {
                        div(classes = "navbar") {
                            a(href = "/") { button(classes = "nav-button") { +"Home" } }
                            a(href = "/about") {
                                button(classes = "nav-button active") { +"About" }
                            }
                        }

                        main(classes = "content-box") {
                            div(classes = "thesis-badge") { +"MASTER'S THESIS PROJECT" }

                            h1 { +"About Mini-Mult System" }

                            h2 { +"Minnesota Multiphasic Personality Inventory" }
                            p {
                                +"""The MMPI is a psychological assessment tool developed to identify 
                                personality structure and psychopathology. This Mini-Mult version 
                                provides a shortened (71 items) yet valid alternative to the full 
                                567-item test."""
                            }

                            h3 { +"Key Features:" }
                            ul(classes = "feature-list") {
                                li { +"Standardized clinical scales for accurate assessment" }
                                li { +"Validity scales to detect response biases" }
                                li { +"Time-efficient administration (20-30 minutes)" }
                                li { +"Research-backed methodology" }
                            }

                            div(classes = "creator-section") {
                                h3 { +"About the Author" }
                                div(classes = "placeholder") {
                                    em {
                                        +"Amin Astezhev, student at KUBSU."
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}