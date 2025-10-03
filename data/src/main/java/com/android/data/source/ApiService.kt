package com.android.data.source

import com.android.data.model.CharactersResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("https://rickandmortyapi.com/api/character")
    suspend fun getAllCharacters(@Query("page") page: Int): CharactersResponse
}