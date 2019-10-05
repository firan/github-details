package com.example.myapplication.usecase.common

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.graphics.drawable.DrawableCompat
import kotlin.math.roundToInt

fun Context.loadColoredDrawable(resourceInt: Int, color: Int): Drawable {
    val bg = AppCompatResources.getDrawable(this, resourceInt)?.mutate()
    if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP) {
        val wrappedBg = DrawableCompat.wrap(bg!!)
        DrawableCompat.setTint(wrappedBg!!, color)
        return wrappedBg
    }
    DrawableCompat.setTint(bg!!, color)
    return bg
}

fun Context.convertDPToPixel(dp: Int): Int {
    val scale = this.resources.displayMetrics.density
    return (dp * scale + 0.5f).roundToInt()
}