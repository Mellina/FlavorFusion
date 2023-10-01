package com.bassul.testing.model

import com.bassul.core.domain.model.Recipe

class RecipeFactory {

    fun create(recipe: Recipe) = when (recipe) {
        Recipe.Pasta -> Recipe(0, "Pasta", "")
        Recipe.Juice -> Recipe(1, "Juice", "")
    }

    sealed class Recipe {
        object Pasta : Recipe()
        object Juice : Recipe()
    }

}