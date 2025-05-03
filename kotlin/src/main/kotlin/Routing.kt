package com.alanpugachev

import com.alanpugachev.routes.homeRoute
import com.alanpugachev.routes.resultsRoute
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.routing.*
import routes.aboutRoute
import routes.surveyRoute

fun Application.configureRouting() {
    routing {
        staticResources("/static", "static")

        homeRoute()
        aboutRoute()
        surveyRoute()
        resultsRoute()
    }
}
