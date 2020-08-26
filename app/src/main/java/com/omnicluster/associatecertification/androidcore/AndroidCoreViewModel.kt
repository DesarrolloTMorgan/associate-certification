package com.omnicluster.associatecertification.androidcore

import android.view.View
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import com.google.android.material.snackbar.Snackbar
import com.omnicluster.associatecertification.R

class AndroidCoreViewModel : ViewModel() {

    fun buildCustomSnackBar(message: String, @ColorRes color: Int, view: View): Snackbar {
        val customSnackbar = Snackbar.make(view, "", Snackbar.LENGTH_SHORT)
        if (message.isNotBlank() && message.isNotEmpty()) {
            customSnackbar.setText(message)
            if (color != -1) {
                customSnackbar.view.setBackgroundColor(ContextCompat.getColor(view.context, color))
            }
        } else {
            customSnackbar.setText(view.context.getString(R.string.empty_snackbar_message))
            customSnackbar.view.setBackgroundColor(
                ContextCompat.getColor(
                    view.context,
                    R.color.errorColor
                )
            )
        }
        return customSnackbar
    }

}