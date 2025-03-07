package com.example.caresync.utils

fun Clamp(value: Float, min: Float, max: Float) : Float {
    if (value in min .. max) return value
    return if (value < min) min else max
}