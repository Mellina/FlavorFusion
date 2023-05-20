package com.bassul.testing.model

import com.bassul.core.domain.model.Recipe

class RecipeFactory {

    fun create(recipe: Recipe) = when (recipe) {
        Recipe.Juice -> Recipe("Juice", "")
        Recipe.Pasta -> Recipe("Pasta", "")
    }

    sealed class Recipe {
        object Pasta : Recipe()
        object Juice : Recipe()
    }

}