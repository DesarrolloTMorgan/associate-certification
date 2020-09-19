package com.omnicluster.associatecertification.util

import android.content.Intent

object IntentUtil {

    fun createOpenPDFIntent() = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
        addCategory(Intent.CATEGORY_OPENABLE)
        type = "application/pdf"
    }
}