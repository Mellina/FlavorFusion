package com.bassul.testing.model

import com.bassul.core.domain.model.ExtendedIngredient
import com.bassul.core.domain.model.RecipeDetails

class DetailRecipeFactory {

    fun create(detailRecipe: FakeDetailRecipe) = when (detailRecipe) {
        FakeDetailRecipe.FakeDetailRecipe1 -> RecipeDetails(
            5645,
            listOf(ExtendedIngredient("onion", 12.0, "", "")),
            listOf(""),
            2,
            60
        )
    }
    sealed class FakeDetailRecipe {
        object FakeDetailRecipe1 : FakeDetailRecipe()
    }
}


