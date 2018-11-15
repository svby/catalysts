package catalysts

import java.awt.Toolkit
import java.awt.datatransfer.StringSelection

fun printf(format: String, vararg args: Any?) {
    System.out.printf(format, *args)
}

fun copyToClipboard(string: String) {
    val selection = StringSelection(string)
    val clipboard = Toolkit.getDefaultToolkit().getSystemClipboard()
    clipboard.setContents(selection, selection)
}

fun output(vararg values: Any, adapter: (Any) -> String = { it.toString() }) {
    val message = values.joinToString(" ", transform = adapter)

    copyToClipboard(message)
    println(message)
}