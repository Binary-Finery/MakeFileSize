package com.spencerstudios.makefilesize

import java.util.*

fun formatBytes(bytes : Long) : String {

    var unit = "B"
    var size: Double = bytes.toDouble()
    when {
        bytes >= GB -> {
            unit = "GB"
            size /= GB
        }
        bytes >= MB -> {
            unit = "MB"
            size /= MB
        }
        bytes >= KB -> {
            unit = "KB"
            size /= KB
        }
    }

    return when {
        size % 1 == .0 -> String.format(Locale.getDefault(), "%,d %s", size.toInt(), unit)
        else -> String.format(Locale.getDefault(), "%,.2f %s", size, unit)
    }
}