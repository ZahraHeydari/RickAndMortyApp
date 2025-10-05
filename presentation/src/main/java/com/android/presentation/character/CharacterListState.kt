package com.android.presentation.character

import com.android.domain.model.Character

data class CharacterListState(
    val characters: List<Character> = emptyList(),
    val errorMessage: String? = null,
    val isLoading : Boolean = false
)