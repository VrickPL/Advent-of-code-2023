import java.io.File
import java.io.InputStream
import java.nio.file.Paths

object TxtReader {
    private val absolutePath = Paths.get("").toAbsolutePath().toString()
    private val basicPath = "${absolutePath}/src/main/txts/"

    fun getStringFromTxt(path: String): String {
        val inputStream: InputStream = File("${basicPath}${path}").inputStream()
        val inputString = inputStream.bufferedReader().use { it.readText() }

        return inputString
    }
}