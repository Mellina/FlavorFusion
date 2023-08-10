package com.bassul.flavorfusion.framework.network.response

import com.bassul.core.domain.model.Recipe
import com.google.gson.annotations.SerializedName

data class RecipeResponse(
    @SerializedName("id")
    val id: Long,
    @SerializedName("title")
    val title: String,
    @SerializedName("image")
    val image: String
)

fun RecipeResponse.toRecipeModel(): Recipe {
    return Recipe(
        id = this.id,
        title = this.title,
        image = this.image
    )
}