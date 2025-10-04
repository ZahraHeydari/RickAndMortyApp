package com.android.presentation.preview

import com.android.domain.model.Character
import com.android.domain.model.Location
import com.android.domain.model.Origin

val ethanCharacter = Character(
    name = "Ethan",
    status = "Unknown", // Part of "Unknown - Human"
    species = "Human",   // Part of "Unknown - Human"
    location = Location(
        name = "Earth (C-137)",
        url = "https://rickandmortyapi.com/api/location/1"
    ),
    // The first episode's name is "Anatomy Park," which corresponds to episode 3
    episode = listOf("https://rickandmortyapi.com/api/episode/3"),

    // Other fields from the model (placeholders or common API data for Ethan):
    id = 330, // Correct ID for Ethan
    image = "https://rickandmortyapi.com/api/character/avatar/330.jpeg", // Correct image URL for Ethan
    gender = "Male",
    type = "",
    created = "2017-11-04T20:29:49.035Z",
    url = "https://rickandmortyapi.com/api/character/330",
    origin = Origin(
        name = "Earth (C-137)",
        url = "https://rickandmortyapi.com/api/location/1"
    )
)