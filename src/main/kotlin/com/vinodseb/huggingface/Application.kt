package com.vinodseb.huggingface

import ai.djl.modality.nlp.qa.QAInput

fun main() {
    val question = "What was the movement co-founded by Pablo Picasso?"
    val paragraph = ("Pablo Ruiz Picasso was a Spanish painter, sculptor, printmaker, ceramicist and theatre designer who spent most of his adult life in France. One of the most influential artists of the 20th century, he is known for co-founding the Cubist movement, the invention of constructed sculpture, the co-invention of collage, and for the wide variety of styles that he helped develop and explore.")
    val input = QAInput(question, paragraph)
    val answer = HuggingFaceQaInference.predict(input)
    println("The answer is: \n$answer")
}
