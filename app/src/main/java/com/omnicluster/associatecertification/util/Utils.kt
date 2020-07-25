package com.omnicluster.associatecertification.util

import android.app.Activity
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import com.omnicluster.associatecertification.R


/**
 * Base to don't get the package name wrapped
 * */
class Utils {
    companion object {
        private var currentTheme = 0

        private const val THEME_MATERIAL_LIGHT = 0
        private const val THEME_YOUR_CUSTOM_THEME = 1

        fun changeToTheme(activity: Activity, theme: Int) {
            currentTheme = theme
            activity.finish()
            activity.startActivity(Intent(activity, activity::class.java))
            activity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }

        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        fun onActivityCreateSetTheme(activity: Activity) {
            when (currentTheme) {
                THEME_MATERIAL_LIGHT -> activity.setTheme(R.style.BaseAppTheme)
                THEME_YOUR_CUSTOM_THEME -> activity.setTheme(R.style.CustomOldLight)
                else -> activity.setTheme(android.R.style.Theme_Material_Light)
            }
        }

    }

}