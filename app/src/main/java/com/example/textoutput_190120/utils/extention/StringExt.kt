package com.example.textoutput_190120.utils.extention

import android.annotation.SuppressLint
import java.text.SimpleDateFormat

private const val FORMAT_DATE_FROM_API = "yyyy-MM-dd'T'HH:mm:ss"
private const val FORMAT_DATE = "dd.MM.yyyy"
private const val FORMAT_TIME = "HH:mm"

@SuppressLint("SimpleDateFormat")
fun String.parseDate(): String {
    val parser = SimpleDateFormat(FORMAT_DATE_FROM_API)
    val formatter = SimpleDateFormat(FORMAT_DATE)
    return formatter.format(parser.parse(this.replace("Z", "")))
}

@SuppressLint("SimpleDateFormat")
fun String.parseTime(): String {
    val parser = SimpleDateFormat(FORMAT_DATE_FROM_API)
    val formatter = SimpleDateFormat(FORMAT_TIME)
    return formatter.format(parser.parse(this.replace("Z", "")))
}
