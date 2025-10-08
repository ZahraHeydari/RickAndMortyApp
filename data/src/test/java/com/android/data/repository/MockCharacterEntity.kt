package com.android.data.repository

import com.android.data.model.CharacterEntity
import com.android.data.model.LocationEntity
import com.android.data.model.OriginEntity

val mockCharacterEntity = CharacterEntity(
    id = 1,
    name = "Rick Sanchez",
    status = "Alive",
    species = "Human",
    type = "",
    gender = "Male",
    origin = OriginEntity("Earth (C-137)", "https://rickandmortyapi.com/api/location/1"),
    location = LocationEntity("Citadel of Ricks", "https://rickandmortyapi.com/api/location/3"),
    image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
    episode = listOf("https://rickandmortyapi.com/api/episode/1"),
    url = "https://rickandmortyapi.com/api/character/1",
    created = "2017-11-04T18:48:46.250Z"
)