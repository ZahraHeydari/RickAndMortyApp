package com.android.data.repository

import com.android.data.mapper.toCharacter
import com.android.data.source.ApiService
import com.android.domain.model.Character
import com.android.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CharacterRepositoryImp @Inject constructor(val apiService: ApiService) : CharacterRepository {

    override suspend fun fetchAllCharacters(page: Int): List<Character> {
        return apiService.getAllCharacters(page).results.map {
            it.toCharacter()
        }
    }
}