package net.dinkla.raytracerchallenge

import java.text.SimpleDateFormat
import java.util.Date

private val df = SimpleDateFormat("yyyyMMddHHmmss")

fun getTimeStamp(): String = df.format(Date())

fun addTimeStamp(fileName: String) = "${getTimeStamp()}_${fileName}"

fun prefixFileName(fileName: String) = "../" + addTimeStamp(fileName)
