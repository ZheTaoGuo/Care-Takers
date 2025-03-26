package com.example.caresync.ui.screens.medication

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.caresync.R
import com.example.caresync.model.Frequency
import com.example.caresync.model.Medication
import com.example.caresync.model.MedicationType
import java.util.Date

@Composable
fun MedicationCard(
    medication: Medication,
    modifier: Modifier
) {
    val (cardColor, boxColor, textColor) = medication.type.getCardColor()
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(30.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(cardColor),
        )
    ) {
        Row(
            modifier = Modifier.padding(24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(2f),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = medication.name,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleLarge,
                    color = Color(boxColor)
                )

                val doseAndType = "${medication.totalDosage} ${
                    stringResource(
                        when (medication.type) {
                            MedicationType.TABLET -> R.string.tablet
                            MedicationType.CAPSULE -> R.string.capsule
                            MedicationType.SYRUP -> R.string.type_syrup
                            MedicationType.DROPS -> R.string.drops
                            MedicationType.SPRAY -> R.string.spray
                            MedicationType.GEL -> R.string.gel
                        }
                    ).lowercase()
                }"

                Text(
                    text = doseAndType,
                    color = Color(boxColor)
                )
            }

            Box(
                modifier = Modifier
                    .height(64.dp)
                    .aspectRatio(1f)
                    .border(
                        width = 1.5.dp, color = Color(boxColor), shape = RoundedCornerShape(16.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(
                        when (medication.type) {
                            MedicationType.TABLET -> R.drawable.ic_tablet
                            MedicationType.CAPSULE -> R.drawable.ic_capsule
                            MedicationType.SYRUP -> R.drawable.ic_syrup
                            MedicationType.DROPS -> R.drawable.ic_drops
                            MedicationType.SPRAY -> R.drawable.ic_spray
                            MedicationType.GEL -> R.drawable.ic_gel
                        }
                    ),
                    contentDescription = stringResource(
                        when (medication.type) {
                            MedicationType.TABLET -> R.string.tablet
                            MedicationType.CAPSULE -> R.string.capsule
                            MedicationType.SYRUP -> R.string.type_syrup
                            MedicationType.DROPS -> R.string.drops
                            MedicationType.SPRAY -> R.string.spray
                            MedicationType.GEL -> R.string.gel
                        }
                    ),
                    modifier = Modifier.size(42.dp),
                    tint = Color(boxColor)
                )
            }
        }
    }
}

@Preview
@Composable
private fun MedicationCardTakenPreview() {
    MedicationCard(
        Medication(
            id = 123L,
            name = "Medication Card Sample",
            frequency = Frequency.THRICE,
            amtPerDosage = 1,
            totalDosage = 10,
            startDate = Date(),
            type = MedicationType.TABLET
        ),
        modifier = TODO()
    )
}
