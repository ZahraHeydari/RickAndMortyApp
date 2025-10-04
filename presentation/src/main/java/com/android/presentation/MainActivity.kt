package com.android.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.android.presentation.character.CharactersScreen
import com.android.presentation.character.CharactersViewModel
import com.android.presentation.ui.theme.RickAndMortyAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RickAndMortyAppTheme {
                val viewModel = hiltViewModel<CharactersViewModel>()
                val charactersState by viewModel.charactersStateFlow.collectAsState()
                CharactersScreen(charactersState)
            }
        }
    }
}