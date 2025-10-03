package com.android.data.repository

import com.android.data.source.ApiService
import com.android.domain.model.Character
import com.android.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CharacterRepositoryImp(val apiService: ApiService) : CharacterRepository {

    override suspend fun fetchAllCharacters(page: Int): List<Character> {
        return apiService.getAllCharacters(page).results.map {
            TODO("map the object")
        }
    }
}