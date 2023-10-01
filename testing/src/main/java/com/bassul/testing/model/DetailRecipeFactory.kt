package com.bassul.testing.model

import com.bassul.core.domain.model.ExtendedIngredient
import com.bassul.core.domain.model.RecipeDetails

class DetailRecipeFactory {

    fun create(detailRecipe: FakeDetailRecipe) = when (detailRecipe) {
        FakeDetailRecipe.FakeCheeseMacaroni -> RecipeDetails(
            5645,
            listOf(ExtendedIngredient("Cheese macaroni", 12.0, "", "")),
            listOf(""),
            2,
            60
        )
    }
    sealed class FakeDetailRecipe {
        object FakeCheeseMacaroni : FakeDetailRecipe()
    }
}


