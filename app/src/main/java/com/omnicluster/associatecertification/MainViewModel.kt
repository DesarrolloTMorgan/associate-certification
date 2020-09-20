package com.omnicluster.associatecertification

import android.os.Build
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    companion object {
        /**
         * The FileUtil class that handle de PDF the thumbnail and document details uses PdfRenderer
         * from android.graphics.pdf and requires minimum API Level 21
         * */
        fun canShowPDFReader(): Boolean {
            return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
        }
    }

}