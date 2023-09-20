package com.vinodseb.huggingface

import com.github.jknack.handlebars.Handlebars
import com.github.jknack.handlebars.Template
import com.github.jknack.handlebars.io.ClassPathTemplateLoader

import com.github.jknack.handlebars.io.TemplateLoader

object Renderer {
    var handlebars: Handlebars
    var template: Template

    init {
        val loader: TemplateLoader = ClassPathTemplateLoader()
        loader.prefix = "/templates"
        handlebars = Handlebars(loader)

        template = handlebars.compile("landingPage")
    }
}
