package routes

import io.ktor.server.html.*
import io.ktor.server.routing.*
import kotlinx.html.*

fun Route.surveyRoute() {
    get("/survey") {
        call.respondHtml {
            head {
                title { +"Survey" }
                link(href = "/static/styles.css", rel = "stylesheet")
                link(href = "https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600&display=swap",
                    rel = "stylesheet")
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
                            a(href = "/results") {
                                button(classes = "nav-button active") { +"Results" }
                            }
                        }

                        main(classes = "content-box") {
                            h1 { +"Psychological Assessment" }

                            div(classes = "disclaimer-box") {
                                +"Please answer honestly based on your feelings. There are no right or wrong answers."
                            }

                            form(classes = "survey-form") {
                                div(classes = "survey-question") {
                                    h3 { +"How often do you feel anxious in social situations?" }

                                    div(classes = "rating-scale") {
                                        span { +"1 - Never" }
                                        span { +"2 - Rarely" }
                                        span { +"3 - Sometimes" }
                                        span { +"4 - Often" }
                                        span { +"5 - Always" }
                                    }

                                    div(classes = "radio-group") {
                                        (1..5).forEach { num ->
                                            div(classes = "radio-option") {
                                                input(type = InputType.radio, name = "anxiety-level") {
                                                    id = "option$num"
                                                    value = "$num"
                                                }
                                                label {
                                                    htmlFor = "option$num"
                                                    +"$num"
                                                }
                                            }
                                        }
                                    }
                                }

                                button(type = ButtonType.submit, classes = "submit-button") {
                                    +"Submit Answer"
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}