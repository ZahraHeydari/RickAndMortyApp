package com.android.domain.usecase

import com.android.domain.model.Character
import com.android.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow

class GetAllCharactersUseCase(val repository: CharacterRepository) {

    suspend fun fetchAllCharacters(page: Int): Result<List<Character>> =
        try {
            Result.success(repository.fetchAllCharacters(page))
        } catch (ex: Exception) {
            Result.failure(ex)
        }
}