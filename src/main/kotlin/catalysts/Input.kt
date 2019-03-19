package catalysts

import java.awt.Toolkit
import java.io.File
import java.util.*

fun level(directory: File? = null, force: List<String>? = null, handler: (Scanner) -> Unit) {
    Scanner(System.`in`).use { stdin ->
        val target = directory ?: File(System.getProperty("input") ?: run {
            println("No input file specified in system properties, falling back to stdin")
            Scanner(System.`in`).use { handler(it) }
            return
        })

        val files = target.listFiles()?.toList()?.sortedBy { it.nameWithoutExtension } ?: emptyList()

        for (file in force?.let { f -> files.filter { it.nameWithoutExtension in f } } ?: files) {
            println("Processing $file")
            Scanner(file).use { handler(it) }
            Toolkit.getDefaultToolkit().beep()
            println("Execution for input ${file.name} complete, press enter to continue")
            stdin.nextLine()
        }
    }
}