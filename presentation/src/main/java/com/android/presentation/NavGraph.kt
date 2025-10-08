package com.android.presentation

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

@Composable
fun NavGraph() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "main") {
        composable("main") {
            val viewModel = hiltViewModel<CharactersViewModel>()
            val characterListState by viewModel.characterListStateFlow.collectAsState()
            CharacterListScreen(
                characterListState,
                { characterId ->
                    navController.navigate("details/${characterId}")
                },
                {
                    viewModel.fetchAllCharacters(++viewModel.page)
                })
        }
        composable(
            route = "details/{id}",
            arguments = listOf(navArgument("id") { type = NavType.StringType })
        ) {
            val viewModel = hiltViewModel<DetailsViewModel>()
            val charactersState by viewModel.characterStateFlow.collectAsState()
            DetailsScreen(charactersState)
        }
    }
}