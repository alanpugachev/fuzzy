package com.alanpugachev

import com.alanpugachev.routes.homeRoute
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        staticResources("/static", "static")

        homeRoute()
    }
}
