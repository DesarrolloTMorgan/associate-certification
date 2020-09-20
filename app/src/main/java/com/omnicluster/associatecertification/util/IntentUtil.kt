package com.omnicluster.associatecertification.util

import android.content.Intent

object IntentUtil {

    const val READ_REQUEST_CODE = 100

    fun createOpenPDFIntent() = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
        addCategory(Intent.CATEGORY_OPENABLE)
        type = "application/pdf"
    }
}