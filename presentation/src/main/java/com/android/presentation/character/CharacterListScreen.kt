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
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
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
import com.android.presentation.R
import com.android.presentation.preview.ethanCharacter
import com.android.presentation.util.showToast

@Composable
fun CharacterListScreen(
    characterListState: CharacterListState,
    onDetailsClick: (characterId: Int) -> Unit,
    onLoadMore: () -> Unit
) {
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
            CharacterList(innerPadding, characterListState.characters, onDetailsClick, onLoadMore)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar() {
    CenterAlignedTopAppBar(
        title = {
            Text(
                stringResource(R.string.app_name),
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
        }
    )
}

@Composable
fun CharacterList(
    innerPadding: PaddingValues,
    characters: List<Character>?,
    onDetailsClick: (characterId: Int) -> Unit,
    onLoadMore: () -> Unit
) {
    val lazyListState = rememberLazyListState()

    val shouldLoadMore = remember {
        derivedStateOf {
            val lastVisibleItem = lazyListState.layoutInfo.visibleItemsInfo.lastOrNull()
            lastVisibleItem != null && lazyListState.layoutInfo.totalItemsCount < 43 &&
                    lastVisibleItem.index == lazyListState.layoutInfo.totalItemsCount - 1
        }
    }

    LaunchedEffect(shouldLoadMore.value) {
        if (shouldLoadMore.value) {
            onLoadMore()
        }
    }

    LazyColumn(
        state = lazyListState,
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
    ) {
        characters?.let {
            items(characters) { character ->
                CharacterInfoCard(character, onDetailsClick)
            }
        }

        if (shouldLoadMore.value) {
            item {
                Text(modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp), text = "Load more...")
            }
        }
    }
}

@Composable
fun CharacterInfoCard(
    character: Character,
    onDetailsClick: (characterId: Int) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        onClick = { onDetailsClick(character.id) }
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
                    fontWeight = FontWeight.Bold
                )

                Row(verticalAlignment = Alignment.CenterVertically) {
                    val statusColor = when (character.status) {
                        "Alive" -> Color.Green
                        "Dead" -> Color.Red
                        else -> MaterialTheme.colorScheme.outline
                    }

                    Canvas(modifier = Modifier.size(8.dp)) {
                        drawCircle(color = statusColor)
                    }

                    Spacer(modifier = Modifier.width(4.dp))

                    Text(
                        text = character.status,
                        style = MaterialTheme.typography.bodyLarge,
                        color = statusColor
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Last known location:",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.outline
                )

                Text(
                    text = character.location.name,
                    style = MaterialTheme.typography.bodyLarge
                )

                Spacer(modifier = Modifier.height(8.dp))

                val genderSpeciesText =
                    listOfNotNull(character.species, character.gender).joinToString(" - ")
                Text(
                    text = "Species:",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.outline
                )

                Text(
                    text = genderSpeciesText,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCharacterInfoCard() {
    MaterialTheme {
        val characterListState = CharacterListState(characters = listOf(ethanCharacter))
        CharacterListScreen(characterListState, {}, {})
    }
}