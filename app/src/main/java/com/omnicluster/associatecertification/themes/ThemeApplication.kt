package com.omnicluster.associatecertification.themes

import android.app.Application

class ThemeApplication : Application() {
    companion object {
        var currentPosition: Int = 0
    }
}