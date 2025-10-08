package com.android.domain.usecase

import com.android.domain.model.Character
import com.android.domain.repository.CharacterRepository
import javax.inject.Inject

class GetCharacterUseCase @Inject constructor(val repository: CharacterRepository) {
    suspend fun getDetails(id : String): Result<Character> =
        runCatching { repository.getDetails(id) }
}