package com.android.presentation.character

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.android.domain.model.Character
import com.android.domain.model.Location
import com.android.domain.model.Origin
import com.android.presentation.R
import com.android.presentation.ui.theme.DarkGray
import com.android.presentation.ui.theme.LightGray
import com.android.presentation.util.showToast

@Composable
fun CharactersScreen(characterListState: CharacterListState) {

    val context = LocalContext.current

    Scaffold(
        topBar = { TopAppBar() }
    ) { innerPadding ->
        val errorMessage = characterListState.errorMessage
        if (errorMessage?.isNotEmpty() == true) {
            context.showToast(errorMessage)
        }
        if (characterListState.characters.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(modifier = Modifier.padding(innerPadding))
            }
        } else {
            CharacterList(innerPadding, characterListState.characters)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar() {
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = DarkGray
        ),
        title = {
            Text(
                stringResource(R.string.app_name),
                color = Color.White,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
        }
    )
}

@Composable
fun CharacterList(
    innerPadding: PaddingValues,
    characters: List<Character>?
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
    ) {
        characters?.let {
            items(characters) { character ->
                CharacterInfoCard(
                    character,
                )
            }
        }
    }
}

@Composable
fun CharacterInfoCard(character: Character) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = LightGray
        )
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = character.image,
                contentDescription = "${character.name}'s image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(150.dp)
                    .clip(MaterialTheme.shapes.small)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = character.name,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Canvas(modifier = Modifier.size(8.dp)) {
                        drawCircle(color = Color.LightGray)
                    }
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = character.status,
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.LightGray
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Last known location:",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.LightGray
                )
                Text(
                    text = character.location?.name ?: "Unknown",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Species:",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.LightGray
                )
                Text(
                    text = character.species,
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.White
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCharacterInfoCard() {
    val ethanCharacter = Character(
        name = "Ethan",
        status = "Unknown", // Part of "Unknown - Human"
        species = "Human",   // Part of "Unknown - Human"
        location = Location(
            name = "Earth (C-137)",
            url = "https://rickandmortyapi.com/api/location/1"
        ),
        // The first episode's name is "Anatomy Park," which corresponds to episode 3
        episode = listOf("https://rickandmortyapi.com/api/episode/3"),

        // Other fields from the model (placeholders or common API data for Ethan):
        id = 330, // Correct ID for Ethan
        image = "https://rickandmortyapi.com/api/character/avatar/330.jpeg", // Correct image URL for Ethan
        gender = "Male",
        type = "",
        created = "2017-11-04T20:29:49.035Z",
        url = "https://rickandmortyapi.com/api/character/330",
        origin = Origin(
            name = "Earth (C-137)",
            url = "https://rickandmortyapi.com/api/location/1"
        )
    )
    CharacterInfoCard(ethanCharacter)
}