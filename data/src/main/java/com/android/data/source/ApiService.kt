package com.android.data.source

import com.android.data.di.BASE_URL
import com.android.data.model.CharacterEntity
import com.android.data.model.CharactersResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("${BASE_URL}character")
    suspend fun getAllCharacters(@Query("page") page: Int): CharactersResponse

    @GET("${BASE_URL}character/{id}")
    suspend fun getCharacterDetails(@Path("id") id: String): CharacterEntity
}