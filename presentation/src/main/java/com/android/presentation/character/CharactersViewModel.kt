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
    private var totalPages = 1
    var page = 1

    init {
        fetchAllCharacters(page)
    }

    fun fetchAllCharacters(page: Int) {
        if (page > totalPages) return
        _characterListStateFlow.update {
            it.copy(isLoading = true)
        }
        viewModelScope.launch {
            val result = getAllCharactersUseCase.fetchAllCharacters(page)
            result.onSuccess { list ->
                totalPages = list.second
                _characterListStateFlow.update {
                    it.copy(
                        characters = it.characters + list.first,
                        isLoading = false,
                        errorMessage = null
                    )
                }
            }.onFailure { ex ->
                _characterListStateFlow.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = ex.message ?: "Unknown error"
                    )
                }
            }
        }
    }
}