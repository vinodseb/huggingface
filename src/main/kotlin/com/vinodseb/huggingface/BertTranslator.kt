package com.vinodseb.huggingface

import ai.djl.modality.nlp.DefaultVocabulary
import ai.djl.modality.nlp.Vocabulary
import ai.djl.modality.nlp.bert.BertToken
import ai.djl.modality.nlp.bert.BertTokenizer
import ai.djl.modality.nlp.qa.QAInput
import ai.djl.ndarray.NDArray
import ai.djl.ndarray.NDList
import ai.djl.ndarray.NDManager
import ai.djl.translate.Translator
import ai.djl.translate.TranslatorContext
import java.io.IOException
import java.nio.file.Path
import java.nio.file.Paths
import java.util.*

class BertTranslator : Translator<QAInput?, String?> {
    private lateinit var tokens: List<String>
    private lateinit var vocabulary: Vocabulary
    private lateinit var tokenizer: BertTokenizer

    @Throws(IOException::class)
    override fun prepare(ctx: TranslatorContext?) {
        val path: Path = Paths.get("src/main/resources/bert-base-cased-vocab.txt")

        vocabulary = DefaultVocabulary
            .builder()
            .optMinFrequency(1)
            .addFromTextFile(path)
            .optUnknownToken("[UNK]")
            .build()

        tokenizer = BertTokenizer()
    }

    override fun processInput(ctx: TranslatorContext?, input: QAInput?): NDList {

        val token: BertToken = tokenizer.encode(
            input?.question?.lowercase(Locale.getDefault()).orEmpty(),
            input?.paragraph?.lowercase(Locale.getDefault()).orEmpty()
        )

        // get the encoded tokens that would be used in processOutput
        tokens = token.tokens
        val manager: NDManager? = ctx?.ndManager

        // map the tokens(String) to indices(long)
        val indices: LongArray = tokens.stream().mapToLong(vocabulary::getIndex).toArray()
        val attentionMask: LongArray = token.attentionMask.stream().mapToLong { i -> i }.toArray()
        val tokenType: LongArray = token.tokenTypes.stream().mapToLong { i -> i }.toArray()
        val indicesArray: NDArray? = manager?.create(indices)
        val attentionMaskArray: NDArray? = manager?.create(attentionMask)
        val tokenTypeArray: NDArray? = manager?.create(tokenType)

        // The order matters
        return NDList(indicesArray, attentionMaskArray, tokenTypeArray)
    }

    override fun processOutput(ctx: TranslatorContext?, list: NDList): String? {
        val startLogits: NDArray = list[0]
        val endLogits: NDArray = list[1]
        val startIdx = startLogits.argMax().getLong().toInt()
        val endIdx = endLogits.argMax().getLong().toInt()
        return tokenizer.tokenToString(tokens.subList(startIdx, endIdx + 1))
    }
}