package net.dinkla.raytracerchallenge

class PPM private constructor(val contents: String) {

    private class Appender(val sb: StringBuilder) {
        var lineLength = 0

        fun append(text: String) {
            if (lineLength + text.length > maxLineLength) {
                lineLength = 0
                sb.append("\n")
            }
            sb.append(text)
            lineLength += text.length
        }
    }

    companion object {

        const val maxLineLength = 70

        fun create(c: Canvas): PPM {
            val contents = with(StringBuilder()) {
                appendLine("P3")
                appendLine("${c.width} ${c.height}")
                appendLine("255")
                for (y in 0 until c.height) {
                    var sep = ""
                    val app = Appender(this)
                    for (x in 0 until c.width) {
                        val (r, g, b) = c[x, y].toInt()
                        app.append(sep)
                        app.append("$r ")
                        app.append("$g ")
                        app.append("$b ")
                    }
                    appendLine("")
                }
                toString()
            }
            val stripped = contents.replace(" \n", "\n")
            return PPM(stripped)
        }
    }
}
