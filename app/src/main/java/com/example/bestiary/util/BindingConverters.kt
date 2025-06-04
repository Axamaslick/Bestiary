package com.example.bestiary.util

import android.view.View
import androidx.databinding.BindingConversion

object BindingConverters {
    @BindingConversion
    @JvmStatic
    fun convertBooleanToVisibility(isVisible: Boolean): Int {
        return if (isVisible) View.VISIBLE else View.GONE
    }
}