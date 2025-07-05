package com.uraniumcode.e_walletplus.utils

import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

object InsetsUtils {

    fun applyWindowInsetsPadding(view: View) {
        ViewCompat.setOnApplyWindowInsetsListener(view) { v, insets ->
            val systemBarsInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(
                v.paddingLeft,
                systemBarsInsets.top,
                v.paddingRight,
                systemBarsInsets.bottom
            )
            insets
        }
    }
}