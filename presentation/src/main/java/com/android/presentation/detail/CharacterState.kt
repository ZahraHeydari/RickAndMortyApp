package com.android.presentation.detail

import com.android.domain.model.Character

data class CharacterState(
    val character: Character? = null,
    val errorMessage: String? = null
)