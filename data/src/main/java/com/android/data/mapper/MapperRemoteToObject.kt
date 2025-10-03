package com.android.data.mapper

import com.android.data.model.CharacterEntity
import com.android.data.model.LocationEntity
import com.android.data.model.OriginEntity
import com.android.domain.model.Character
import com.android.domain.model.Location
import com.android.domain.model.Origin

fun CharacterEntity.toCharacter() = Character(
    created = created,
    episode = episode,
    gender = gender,
    id = id,
    image = image,
    location = location.map(),
    name = name,
    origin = origin.map(),
    species = species,
    status = status,
    type = type,
    url = url
)

fun LocationEntity.map() = Location(
    name = name,
    url = url
)

fun OriginEntity.map() = Origin(
    name = name,
    url = url
)