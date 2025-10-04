package com.android.domain.repository

import com.android.domain.model.Character

interface CharacterRepository {
    suspend fun fetchAllCharacters(page: Int): List<Character>
    suspend fun getDetails(id: String): Character
}