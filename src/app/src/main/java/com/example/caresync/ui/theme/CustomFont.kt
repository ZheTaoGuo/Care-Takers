package com.example.caresync.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import com.example.caresync.R

object CustomFont {
    val provider = GoogleFont.Provider(
        providerAuthority = "com.google.android.gms.fonts",
        providerPackage = "com.google.android.gms",
        certificates = R.array.com_google_android_gms_fonts_certs
    )

    @Composable
    fun Commissioner(): FontFamily {
        val fontName = GoogleFont("Commissioner")

        val fontFamily = FontFamily(
            Font(googleFont = fontName, fontProvider = provider)
        )
        return fontFamily
    }

    @Composable
    fun Quicksand(): FontFamily {
        val fontName = GoogleFont("Quicksand")

        val fontFamily = FontFamily(
            Font(googleFont = fontName, fontProvider = provider)
        )

        return fontFamily
    }
}