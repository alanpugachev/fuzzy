package com.alanpugachev.routes

import com.alanpugachev.dba.SurveyResultsTable
import com.alanpugachev.repos.SurveyResultsRepo
import io.ktor.server.html.respondHtml
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import kotlinx.html.a
import kotlinx.html.body
import kotlinx.html.button
import kotlinx.html.div
import kotlinx.html.h1
import kotlinx.html.head
import kotlinx.html.link
import kotlinx.html.main
import kotlinx.html.span
import kotlinx.html.table
import kotlinx.html.td
import kotlinx.html.th
import kotlinx.html.title
import kotlinx.html.tr
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

fun Route.resultsRoute() {
    val surveyResultsRepo = SurveyResultsRepo()

    get("/results") {
        transaction {
            SurveyResultsTable.selectAll().limit(1).forEach { row ->
                println("RAW DB VALUE TYPE: ${row[SurveyResultsTable.results]::class.java}")
                println(row[SurveyResultsTable.results])
            }
        }

        call.respondHtml {
            head {
                title { +"Мини-мульт" }
                link(href = "/static/styles.css", rel = "stylesheet")
                link(href = "https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600&display=swap",
                    rel = "stylesheet")
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
                    }

                    main(classes = "content-box") {
                        h1 { +"Результаты" }
                        table {
                            tr {
                                th { +"Шкала" }
                                th { +"Низкий" }
                                th { +"Средний" }
                                th { +"Повышенный" }
                                th { +"Высокий" }
                                th { +"Очень высокий" }
                            }

                            surveyResultsRepo
                                .getAllRaw()
                                .getOrThrow()
                                .last()
                                .let { result ->
                                    listOf(
                                        "Истерия" to result.hysteria,
                                        "Ипохондрия" to result.hypochondria,
                                        "Депрессия" to result.depression,
                                        "Психопатия" to result.psychopathy,
                                        "Паранойа" to result.paranoia,
                                        "Психостения" to result.psychasthenia,
                                        "Шизофрения" to result.schizophrenia,
                                        "Гипомания" to result.hypomania,
                                        "Интроверсия" to result.introversion
                                    ).forEach { (scaleName, levels) ->
                                        tr {
                                            td {
                                                span("scale-name") { +scaleName }
                                            }
                                            td { +"${"%.4f".format(levels.low)}" }
                                            td { +"${"%.4f".format(levels.elevated)}" }
                                            td { +"${"%.4f".format(levels.mid)}" }
                                            td { +"${"%.4f".format(levels.high)}" }
                                            td { +"${"%.4f".format(levels.very_high)}" }
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