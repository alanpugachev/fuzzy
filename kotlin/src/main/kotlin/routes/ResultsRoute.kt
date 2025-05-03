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
                    }

                    main(classes = "content-box") {
                        h1 { +"Psychological Survey Results" }
                        table {
                            tr {
                                th { +"Scale" }
                                th { +"Low" }
                                th { +"Mid" }
                                th { +"Elevated" }
                                th { +"High" }
                                th { +"Very High" }
                            }

                            surveyResultsRepo
                                .getAllRaw()
                                .getOrThrow()
                                .last()
                                .let { result ->
                                    listOf(
                                        "Hysteria" to result.hysteria,
                                        "Hypochondria" to result.hypochondria,
                                        "Depression" to result.depression,
                                        "Psychopathy" to result.psychopathy,
                                        "Paranoia" to result.paranoia,
                                        "Psychasthenia" to result.psychasthenia,
                                        "Schizophrenia" to result.schizophrenia,
                                        "Hypomania" to result.hypomania,
                                        "Introversion" to result.introversion
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