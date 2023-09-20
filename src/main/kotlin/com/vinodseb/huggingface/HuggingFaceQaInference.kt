package com.vinodseb.huggingface

import ai.djl.modality.nlp.qa.QAInput
import ai.djl.repository.zoo.Criteria
import ai.djl.repository.zoo.ZooModel
import ai.djl.training.util.ProgressBar
import java.nio.file.Paths

object HuggingFaceQaInference {

    private var model: ZooModel<QAInput, String>
    private var translator = BertTranslator()

    init {
        model = Criteria.builder()
            .setTypes(QAInput::class.java, String::class.java)
            .optModelPath(Paths.get("src/main/resources/trace_cased_bertqa.pt"))
            .optTranslator(translator)
            .optProgress(ProgressBar())
            .build()
            .loadModel()
    }

    fun predict(input: QAInput): String =
        kotlin.runCatching {
            model
                .newPredictor(translator)
                .predict(input) ?: "null"
        }.fold(
            onSuccess = {
                it
            },
            onFailure = {
                "${it.message}"
            }
        )
}

