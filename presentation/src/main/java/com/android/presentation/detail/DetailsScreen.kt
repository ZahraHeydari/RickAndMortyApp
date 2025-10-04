package com.android.presentation.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.android.presentation.preview.ethanCharacter
import com.android.presentation.util.showToast

@Composable
fun DetailsScreen(
    characterState: CharacterState
) {
    val context = LocalContext.current
    val errorMessage = characterState.errorMessage
    if (errorMessage?.isNotEmpty() == true) {
        context.showToast(errorMessage)
    }

    val character = characterState.character
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(24.dp))
        // Character Image
        AsyncImage(
            model = character?.image,
            contentDescription = "Image of ${character?.name}",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(24.dp)
                .size(200.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Color.LightGray) // Placeholder color while loading
        )

        Text(
            text = character?.name ?: "Unknown",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp),
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        val type = if (character?.type.isNullOrEmpty()) "Unknown" else character.type
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            CharacterProperty(label = "Status", value = character?.status ?: "Unknown")
            CharacterProperty(label = "Species", value = character?.species ?: "Unknown")
            CharacterProperty(label = "Type", value = type)
            CharacterProperty(label = "Gender", value = character?.gender ?: "Unknown")
            CharacterProperty(
                label = "Origin Location",
                value = character?.origin?.name ?: "Unknown"
            )
            CharacterProperty(
                label = "Last Known Location",
                value = character?.location?.name ?: "Unknown"
            )
        }

        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
fun CharacterProperty(label: String, value: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.outline
        )

        val dividerColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
        Divider(modifier = Modifier.padding(top = 8.dp), color = dividerColor)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCharacterDetailScreen() {
    MaterialTheme {
        DetailsScreen(characterState = CharacterState(character = ethanCharacter))
    }
}