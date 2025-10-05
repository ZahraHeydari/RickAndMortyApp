package com.android.domain.repository

import com.android.domain.model.Character

interface CharacterRepository {
    suspend fun fetchAllCharacters(page: Int): Pair<List<Character>, Int>
    suspend fun getDetails(id: String): Character
}