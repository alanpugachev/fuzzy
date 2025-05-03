package routes

import com.alanpugachev.entities.Answer
import com.alanpugachev.entities.Question
import com.alanpugachev.services.KafkaAnswerProducer
import com.alanpugachev.vo.AnswerValue
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.server.html.*
import io.ktor.server.request.*
import io.ktor.server.response.respondText
import io.ktor.server.routing.*
import kotlinx.coroutines.delay
import kotlinx.html.*
import kotlin.time.Duration.Companion.seconds

fun Route.surveyRoute() {
    val kafkaProducerService = KafkaAnswerProducer()

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
        call.respondText(
            """
            <!DOCTYPE html>
            <html>
            <head>
                <title>Processing Survey</title>
                <style>
                    .loader {
                        border: 8px solid #f3f3f3;
                        border-top: 8px solid #3498db;
                        border-radius: 50%;
                        width: 60px;
                        height: 60px;
                        animation: spin 2s linear infinite;
                        margin: 20px auto;
                    }
                    @keyframes spin {
                        0% { transform: rotate(0deg); }
                        100% { transform: rotate(360deg); }
                    }
                    .timer {
                        text-align: center;
                        font-family: Arial, sans-serif;
                        font-size: 18px;
                        margin-top: 20px;
                    }
                </style>
            </head>
            <body>
                <div class="loader"></div>
                <div class="timer" id="countdown">Processing your results... </div>
                
                <script>
                    let seconds = 5;
                    const countdown = document.getElementById('countdown');
                    
                    const interval = setInterval(() => {
                        seconds--;
                        countdown.textContent = `Processing your results...`;
                        
                        if (seconds <= 0) {
                            clearInterval(interval);
                            window.location.href = '/results'; // Перенаправление после завершения
                        }
                    }, 1000);
                </script>
            </body>
            </html>
        """.trimIndent(),
            ContentType.Text.Html
        )

        val params = call
            .receiveParameters()
            .entries()
            .associate { it.key to it.value.first() }
            .map {
                Answer(it.key, AnswerValue(it.value.toInt()))
            }

        delay(5.seconds)

        runCatching {
            kafkaProducerService.sendMessage(
                topic = "kraftt",
                value = params
            )
        }.onFailure {
            call.respondText("Error processing survey: ${it.message}", status = HttpStatusCode.InternalServerError)
        }
    }
}