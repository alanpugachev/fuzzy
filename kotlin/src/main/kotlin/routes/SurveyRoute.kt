package routes

import com.alanpugachev.entities.Answer
import com.alanpugachev.entities.Question
import com.alanpugachev.services.KafkaAnswerProducer
import com.alanpugachev.services.KafkaResultsConsumer
import com.alanpugachev.vo.AnswerValue
import io.ktor.server.html.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import kotlinx.html.*

fun Route.surveyRoute() {
    val kafkaProducerService = KafkaAnswerProducer()
    val kafkaConsumerService = KafkaResultsConsumer()

    get("/survey") {
        val questions: List<Question> = Question.questions

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

                            form(classes = "survey-form", action = "/submit-survey", method = FormMethod.post) {
                                questions.forEach { question ->
                                    div(classes = "survey-question") {
                                        h3 { +question.text }

                                        div(classes = "rating-scale") {
                                            span { +"Never" }
                                            span { +"Rarely" }
                                            span { +"Sometimes" }
                                            span { +"Often" }
                                            span { +"Always" }
                                        }

                                        div(classes = "radio-group") {
                                            (1..5).forEach { num ->
                                                div(classes = "radio-option") {
                                                    input(type = InputType.radio, name = "q${question.id}") {
                                                        id = "q${question.id}_$num"
                                                        value = "$num"
                                                    }
                                                    label {
                                                        htmlFor = "q${question.id}_$num"
                                                        +"$num"
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }

                                button(type = ButtonType.submit, classes = "submit-button") {
                                    +"Submit"
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    post("/submit-survey") {
        val params = call
            .receiveParameters()
            .entries()
            .associate { it.key to it.value.first() }
            .map {
                Answer(it.key, AnswerValue(it.value.toInt()))
            }

        runCatching {
            kafkaProducerService.sendMessage(
                topic = "kraftt",
                value = params
            )
        }
    }
}