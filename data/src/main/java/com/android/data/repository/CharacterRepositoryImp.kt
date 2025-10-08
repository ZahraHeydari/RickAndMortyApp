package com.android.data.repository

import com.android.data.mapper.toCharacter
import com.android.data.source.ApiService
import com.android.domain.model.Character
import com.android.domain.repository.CharacterRepository
import javax.inject.Inject

class CharacterRepositoryImp @Inject constructor(val apiService: ApiService) : CharacterRepository {

    override suspend fun fetchAllCharacters(page: Int): Pair<List<Character>, Int> {
        val response = apiService.getAllCharacters(page)
        return response.results.map {
            it.toCharacter()
        } to response.info.pages
    }

    override suspend fun getDetails(id: String): Character {
        return apiService.getCharacterDetails(id).toCharacter()
    }
}