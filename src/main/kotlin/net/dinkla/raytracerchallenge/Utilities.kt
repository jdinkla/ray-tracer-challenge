package net.dinkla.raytracerchallenge

import java.io.File
import java.text.SimpleDateFormat
import java.util.Date

const val GENERATED_DIR = "generated"

private val df = SimpleDateFormat("yyyyMMddHHmmss")

fun getTimeStamp(): String = df.format(Date())

fun addTimeStamp(fileName: String) = "${getTimeStamp()}_${fileName}"

fun prefixFileName(fileName: String): String {
    File(GENERATED_DIR).mkdirs()
    return "$GENERATED_DIR/" + addTimeStamp(fileName)
}
