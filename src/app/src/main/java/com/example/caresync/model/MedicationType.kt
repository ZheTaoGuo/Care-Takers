package com.example.caresync.model

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

enum class MedicationType(val displayName: String) {
    TABLET("pills"),
    CAPSULE("capsules"),
    SYRUP("ml"),
    DROPS("drops"),
    SPRAY("sprays"),
    GEL("gels");

    @Composable
    fun getCardColor() = when (this) {
        TABLET -> Triple(
            MaterialTheme.colorScheme.primaryContainer.value,
            MaterialTheme.colorScheme.primary.value,
            MaterialTheme.colorScheme.onPrimaryContainer.value
        )
        CAPSULE -> Triple(
            MaterialTheme.colorScheme.secondaryContainer.value,
            MaterialTheme.colorScheme.secondary.value,
            MaterialTheme.colorScheme.onSecondaryContainer.value
        )
        SYRUP -> Triple(
            MaterialTheme.colorScheme.tertiaryContainer.value,
            MaterialTheme.colorScheme.tertiary.value,
            MaterialTheme.colorScheme.onTertiaryContainer.value
        )
        DROPS -> Triple(
            MaterialTheme.colorScheme.surfaceVariant.value,
            MaterialTheme.colorScheme.primary.value,
            MaterialTheme.colorScheme.onSurfaceVariant.value
        )
        SPRAY -> Triple(
            MaterialTheme.colorScheme.errorContainer.value,
            MaterialTheme.colorScheme.error.value,
            MaterialTheme.colorScheme.onErrorContainer.value
        )
        GEL -> Triple(
            MaterialTheme.colorScheme.inversePrimary.value,
            MaterialTheme.colorScheme.primary.value,
            MaterialTheme.colorScheme.onPrimaryContainer.value
        )
    }
}

fun getMedicationTypes(): List<MedicationType> = MedicationType.values().toList()
