package com.android.domain.usecase

import com.android.domain.model.Character
import com.android.domain.model.Location
import com.android.domain.model.Origin

val rickCharacter = Character(
    id = 1,
    name = "Rick Sanchez",
    status = "Alive",
    species = "Human",
    type = "",
    gender = "Male",
    origin = Origin("Earth (C-137)", "https://rickandmortyapi.com/api/location/1"),
    location = Location("Citadel of Ricks", "https://rickandmortyapi.com/api/location/3"),
    image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
    episode = listOf("https://rickandmortyapi.com/api/episode/1"),
    url = "https://rickandmortyapi.com/api/character/1",
    created = "2017-11-04T18:48:46.250Z"
)

val mortyCharacter = Character(
    id = 2,
    name = "Morty Smith",
    status = "Alive",
    species = "Human",
    type = "",
    gender = "Male",
    origin = Origin("unknown", "https://rickandmortyapi.com/api/location/1"),
    location = Location("Citadel of Ricks", "https://rickandmortyapi.com/api/location/3"),
    image = "https://rickandmortyapi.com/api/character/avatar/2.jpeg",
    episode = listOf(
        "https://rickandmortyapi.com/api/episode/1",
        "https://rickandmortyapi.com/api/episode/2",
        "https://rickandmortyapi.com/api/episode/3",
        "https://rickandmortyapi.com/api/episode/4",
        "https://rickandmortyapi.com/api/episode/5"
    ),
    url = "https://rickandmortyapi.com/api/character/2",
    created = "2017-11-04T18:50:21.651Z"
)