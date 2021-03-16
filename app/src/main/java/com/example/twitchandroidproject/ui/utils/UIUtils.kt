package com.example.twitchandroidproject.ui.utils

import android.graphics.Bitmap
import android.graphics.Canvas
import kotlin.random.Random

/**
 * Function that generates profile image of random color that can be used as initial image
 */
fun createRandomProfilePicture(height: Int = 128, width: Int = 128): Bitmap {
    val bitmap = Bitmap.createBitmap(height, width, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    canvas.drawRGB(Random.nextInt(255), Random.nextInt(255), Random.nextInt(255))
    return bitmap
}