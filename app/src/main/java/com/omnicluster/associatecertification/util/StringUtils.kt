package com.omnicluster.associatecertification.util

import java.text.DecimalFormat
import kotlin.math.pow

object StringUtils {

    /**
     * Convenience method for formatting file size.
     * Taken from: https://stackoverflow.com/a/5599842/2914696
     */
    fun readableFileSize(size: Int): String {
        if (size <= 0) {
            return "0"
        }

        val units = arrayOf("B", "kB", "MB", "GB", "TB")
        val digitGroups = (Math.log10(size.toDouble()) / Math.log10(1024.0)).toInt()

        return DecimalFormat("#,##0.#").format(
            size / 1024.0.pow(digitGroups.toDouble())
        ) + " " + units[digitGroups]
    }
}