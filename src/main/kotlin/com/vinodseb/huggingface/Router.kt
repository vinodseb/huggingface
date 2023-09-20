package com.vinodseb.huggingface

import com.github.jknack.handlebars.Handlebars
import com.github.jknack.handlebars.Template
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.landingPage() = get("/") {
    val handlebars = Handlebars()
    val template: Template = handlebars.compileInline("Hello {{this}}!")
    call.respondText(template.apply("Handlebars.java"))
}
