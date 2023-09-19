import ai.djl.ModelException
import ai.djl.modality.nlp.qa.QAInput
import ai.djl.repository.zoo.Criteria
import ai.djl.training.util.ProgressBar
import ai.djl.translate.TranslateException
import com.vinodseb.huggingface.BertTranslator
import java.io.IOException
import java.nio.file.Paths

object HuggingFaceQaInference {
    @Throws(IOException::class, TranslateException::class, ModelException::class)
    @JvmStatic
    fun main(args: Array<String>) {
        val question = "What was the movement co-founded by Pablo Picasso?"
        val paragraph = ("Pablo Ruiz Picasso was a Spanish painter, sculptor, printmaker, ceramicist and theatre designer who spent most of his adult life in France. One of the most influential artists of the 20th century, he is known for co-founding the Cubist movement, the invention of constructed sculpture, the co-invention of collage, and for the wide variety of styles that he helped develop and explore.")
        val input = QAInput(question, paragraph)
        val answer = qaPredict(input)
        println("The answer is: \n$answer")
    }

    @Throws(IOException::class, TranslateException::class, ModelException::class)
    fun qaPredict(input: QAInput?): String? {
        val translator = BertTranslator()
        val criteria = Criteria.builder()
            .setTypes(QAInput::class.java, String::class.java)
            .optModelPath(Paths.get("src/main/resources/trace_cased_bertqa.pt"))
            .optTranslator(translator)
            .optProgress(ProgressBar()).build()
        val model = criteria.loadModel()
        return model.newPredictor(translator).predict(input)
    }
}