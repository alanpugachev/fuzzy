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
                                button(classes = "nav-button") { +"Главная" }
                            }
                            a(href = "/about") {
                                button(classes = "nav-button") { +"О проекте" }
                            }
                        }

                        main(classes = "content-box") {
                            h1 { +"Оценка Mini-Mult" }

                            div(classes = "highlight-box") {
                                p {
                                    +"""Сокращённая 71-пунктовая версия MMPI, сохраняющая клиническую валидность 
                    при сокращении времени тестирования на 80% по сравнению с полным 567-пунктовым тестом.""".trimMargin()
                                }
                            }

                            h2 { +"Ключевые характеристики" }
                            ul(classes = "feature-list") {
                                li { strong { +"71 пункт (в оригинальном MMPI - 567)" } }
                                li { strong { +"Время прохождения 20-30 минут" } }
                                li { strong { +"10 клинических шкал, как в полном MMPI" } }
                                li { strong { +"3 шкалы валидности (L, F, K) для выявления искажений ответов" } }
                                li { strong { +"Такие же подсчёт и интерпретация, как в стандартном MMPI" } }
                            }

                            h2 { +"Типичные применения" }
                            div(classes = "uses-grid") {
                                div(classes = "use-card") { +"Первичный клинический скрининг" }
                                div(classes = "use-card") { +"Научные исследования" }
                                div(classes = "use-card") { +"Краткая оценка личности" }
                                div(classes = "use-card") { +"Ситуации, требующие быстрой оценки" }
                            }

                            div(classes = "survey-action") {
                                a(href = "/survey") {
                                    button(classes = "survey-button") {
                                        +"Пройти тест"
                                    }
                                }
                            }

                            p(classes = "disclaimer") {
                                +"Примечание: Несмотря на эффективность, Mini-Mult может упускать тонкие нюансы, выявляемые полной версией MMPI."
                            }
                        }
                    }
                }
            }
        }
    }
}