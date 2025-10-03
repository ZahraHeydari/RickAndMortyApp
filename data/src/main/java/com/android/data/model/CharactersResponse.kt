package com.android.data.model

data class CharactersResponse(
    val info: InfoEntity,
    val results: List<CharacterEntity>
)