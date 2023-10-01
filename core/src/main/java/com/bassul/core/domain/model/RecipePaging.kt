package com.bassul.core.domain.model

data class RecipePaging(
    val offset: Int,
    val totalResults: Int,
    val recipes: List<Recipe>
)
