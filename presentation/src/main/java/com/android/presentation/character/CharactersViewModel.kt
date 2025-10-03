package com.android.presentation.character

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.domain.usecase.GetAllCharactersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(
    val getAllCharactersUseCase: GetAllCharactersUseCase
) : ViewModel() {

    private val _charactersStateFlow =
        MutableStateFlow<List<com.android.domain.model.Character>?>(emptyList())
    val charactersStateFlow: StateFlow<List<com.android.domain.model.Character>?> =
        _charactersStateFlow

    init {
        fetchAllCharacters(1)
    }

    fun fetchAllCharacters(page: Int) {
        viewModelScope.launch {
            _charactersStateFlow.value =
                getAllCharactersUseCase.fetchAllCharacters(page).getOrNull()
        }
    }
}