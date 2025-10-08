package com.android.domain.usecase

import com.android.domain.model.Character
import com.android.domain.repository.CharacterRepository
import javax.inject.Inject

class GetAllCharactersUseCase @Inject constructor(val repository: CharacterRepository) {
    suspend fun fetchAllCharacters(page: Int): Result<Pair<List<Character>, Int>> =
        runCatching { repository.fetchAllCharacters(page) }
}