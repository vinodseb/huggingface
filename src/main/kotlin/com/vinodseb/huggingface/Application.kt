package com.vinodseb.huggingface

import io.ktor.server.application.*
import io.ktor.server.netty.*
import io.ktor.server.routing.*
import io.ktor.util.logging.*

private val Log = KtorSimpleLogger("Application")

fun main(args: Array<String>) = EngineMain.main(args)

fun Application.module() {
    Log.info("Running application module")

    install(IgnoreTrailingSlash)

    routing {
        landingPage()
        submit()
    }

    environment.monitor.subscribe(ApplicationStarted){
        println("My app is ready to roll")
    }

    environment.monitor.subscribe(ApplicationStopped){
        println("Time to clean up")
    }
}
