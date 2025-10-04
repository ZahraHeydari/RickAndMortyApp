package com.android.presentation.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.domain.usecase.GetCharacterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    val getCharacterUseCase: GetCharacterUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _characterStateFlow = MutableStateFlow(CharacterState())
    val characterStateFlow: StateFlow<CharacterState> = _characterStateFlow.asStateFlow()

    init {
        val id = savedStateHandle.get<String>("id")
        id?.let { getCharacterDetails(it) }
    }

    fun getCharacterDetails(id: String) {
        viewModelScope.launch {
            val result = getCharacterUseCase.getDetails(id)
            if (result.isSuccess) {
                _characterStateFlow.update {
                    it.copy(
                        character = result.getOrNull(),
                        errorMessage = null
                    )
                }
            } else {
                _characterStateFlow.update {
                    it.copy(
                        character = null,
                        errorMessage = result.exceptionOrNull()?.message ?: "Unknown error"
                    )
                }
            }
        }
    }
}