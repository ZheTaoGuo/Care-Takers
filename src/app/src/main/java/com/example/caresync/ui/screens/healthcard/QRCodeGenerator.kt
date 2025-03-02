package com.example.caresync.ui.screens.healthcard

import android.graphics.Bitmap
import android.graphics.Color
import androidx.compose.foundation.Image
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.common.BitMatrix
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.size

@Composable
fun QRCodeGenerator(content: String) {
    val bitmap = generateQRCodeBitmap(content)
    bitmap?.let {
        Image(bitmap = it.asImageBitmap(), contentDescription = "QR Code", modifier = Modifier.size(150.dp))
    }
}

fun generateQRCodeBitmap(content: String): Bitmap? {
    return try {
        val bitMatrix: BitMatrix = MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, 512, 512)
        val width = bitMatrix.width
        val height = bitMatrix.height
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)

        for (x in 0 until width) {
            for (y in 0 until height) {
                bitmap.setPixel(x, y, if (bitMatrix[x, y]) Color.BLACK else Color.WHITE)
            }
        }
        bitmap
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}