package com.android.presentation.character

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.domain.usecase.GetAllCharactersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(
    val getAllCharactersUseCase: GetAllCharactersUseCase
) : ViewModel() {

    private val _characterListStateFlow = MutableStateFlow(CharacterListState())
    val characterListStateFlow: StateFlow<CharacterListState> = _characterListStateFlow.asStateFlow()

    init {
        fetchAllCharacters(1)
    }

    fun fetchAllCharacters(page: Int) {
        viewModelScope.launch {
            val result = getAllCharactersUseCase.fetchAllCharacters(page)
            if (result.isSuccess) {
                // Update state with successful data and clear error
                _characterListStateFlow.update {
                    it.copy(
                        characters = result.getOrNull().orEmpty(),
                        errorMessage = null
                    )
                }
            } else {
                // Update state with the error message
                _characterListStateFlow.update {
                    it.copy(
                        characters = emptyList(),
                        errorMessage = result.exceptionOrNull()?.message ?: "Unknown error"
                    )
                }
            }
        }
    }
}