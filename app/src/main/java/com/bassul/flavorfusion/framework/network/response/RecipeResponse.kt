package com.bassul.flavorfusion.framework.network.response

import com.bassul.core.domain.model.Recipe

data class RecipeResponse(
    val title: String,
    val image: String
)

fun RecipeResponse.toRecipeModel(): Recipe {
    return Recipe(
        title = this.title,
        image = this.image
    )
}