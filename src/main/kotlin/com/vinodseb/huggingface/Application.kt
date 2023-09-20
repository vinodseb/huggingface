package com.vinodseb.huggingface

import ai.djl.modality.nlp.qa.QAInput
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
    }

    environment.monitor.subscribe(ApplicationStarted){
        println("My app is ready to roll")
    }

    environment.monitor.subscribe(ApplicationStopped){
        println("Time to clean up")
    }
}
fun test() {
    val question = "What was the movement co-founded by Pablo Picasso?"
    val paragraph = ("Pablo Ruiz Picasso was a Spanish painter, sculptor, printmaker, ceramicist and theatre designer who spent most of his adult life in France. One of the most influential artists of the 20th century, he is known for co-founding the Cubist movement, the invention of constructed sculpture, the co-invention of collage, and for the wide variety of styles that he helped develop and explore.")
    val input = QAInput(question, paragraph)
    val answer = HuggingFaceQaInference.predict(input)
    println("The answer is: \n$answer")
}

