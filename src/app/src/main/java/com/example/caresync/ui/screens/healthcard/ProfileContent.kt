package com.example.caresync.ui.screens.healthcard

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.painterResource
import coil3.compose.rememberAsyncImagePainter
import com.example.caresync.R
import com.example.caresync.model.UserProfile

@Composable
fun ProfileContent(userProfile: UserProfile) {
    Column(modifier = Modifier.fillMaxWidth().padding(0.dp)) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start, verticalAlignment = Alignment.CenterVertically) {
            val imagePainter = userProfile.photoUri?.let {
                rememberAsyncImagePainter(it)
            } ?: painterResource(id = R.drawable.default_profile)

            Image(
                painter = imagePainter,
                contentDescription = "Profile Photo",
                modifier = Modifier
                    .size(80.dp)
                    .padding(8.dp)
                    .clip(RoundedCornerShape(50.dp)),
                contentScale = ContentScale.Crop,
            )

            Column(modifier = Modifier.padding(8.dp)) {
                Text(text = "Name: ${userProfile.name}", style = MaterialTheme.typography.bodyLarge)
                Text(text = "DOB: ${userProfile.dateOfBirth}", style = MaterialTheme.typography.bodyLarge)
                Text(text = "Language: ${userProfile.primaryLanguage}", style = MaterialTheme.typography.bodyLarge)
                Text(
                    text = "Organ Donor: ${if (userProfile.organDonation) "Yes" else "No"}",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }

    }
}