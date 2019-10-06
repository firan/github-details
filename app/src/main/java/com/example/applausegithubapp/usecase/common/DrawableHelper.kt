package com.example.applausegithubapp.usecase.common

import android.content.Context
import kotlin.math.roundToInt

fun Context.convertDPToPixel(dp: Int): Int {
    val scale = this.resources.displayMetrics.density
    return (dp * scale + 0.5f).roundToInt()
}