package com.vinodseb.huggingface

import ai.djl.modality.nlp.qa.QAInput
import com.vinodseb.huggingface.Renderer.template
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.util.logging.*

data class Output(val paragraph: String, val question: String, val answer: String)

private val Log = KtorSimpleLogger("Application")

fun Route.landingPage() = get("/") {
    call.respondText(template.apply("Handlebars.java"), ContentType.Text.Html)
}

fun Route.submit() = post("/") {
    val formParameters = call.receiveParameters()
    val paragraph = formParameters["paragraph"].toString()
    val question = formParameters["question"].toString()

    val input = QAInput(question, paragraph)
    val answer = HuggingFaceQaInference.predict(input)

    val output = Output(paragraph, question, answer)
    call.respondText(template.apply(output), ContentType.Text.Html)
}
