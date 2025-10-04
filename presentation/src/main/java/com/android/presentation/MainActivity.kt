package com.android.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.android.presentation.character.CharacterListScreen
import com.android.presentation.character.CharactersViewModel
import com.android.presentation.detail.DetailsScreen
import com.android.presentation.detail.DetailsViewModel
import com.android.presentation.ui.theme.RickAndMortyAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RickAndMortyAppTheme {
                NavGraph()
            }
        }
    }
}

@Composable
fun NavGraph() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "main") {
        composable("main") {
            val viewModel = hiltViewModel<CharactersViewModel>()
            val characterListState by viewModel.characterListStateFlow.collectAsState()
            CharacterListScreen(characterListState, { characterId ->
                navController.navigate("details/${characterId}")
            })
        }
        composable(
            route = "details/{id}",
            arguments = listOf(navArgument("id") { type = NavType.StringType })
        ) { backStackEntry ->
            val viewModel = hiltViewModel<DetailsViewModel>()
            val charactersState by viewModel.characterStateFlow.collectAsState()
            DetailsScreen(charactersState)
        }
    }
}