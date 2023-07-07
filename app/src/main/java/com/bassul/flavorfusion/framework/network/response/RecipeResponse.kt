package com.bassul.flavorfusion.framework.network.response

import com.bassul.core.domain.model.Recipe
import com.google.gson.annotations.SerializedName

data class RecipeResponse(
    @SerializedName("title")
    val title: String,
    @SerializedName("image")
    val image: String
)

fun RecipeResponse.toRecipeModel(): Recipe {
    return Recipe(
        title = this.title,
        image = this.image
    )
}