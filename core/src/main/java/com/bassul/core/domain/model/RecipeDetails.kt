package com.bassul.core.domain.model


data class RecipeDetails(
    val id: Long,
    val extendedIngredients: ExtendedIngredient,
    val dishTypes: List<String>,
    val servings: Int,
    val readyInMinutes: Int
)