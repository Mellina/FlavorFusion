package com.bassul.flavorfusion.framework.network.response

import com.bassul.core.domain.model.RecipeDetails
import com.google.gson.annotations.SerializedName

data class RecipeDetailsResponse(
    @SerializedName("id")
    val id: Long,
    @SerializedName("extendedIngredients")
    val extendedIngredients: List<ExtendedIngredientResponse>,
    @SerializedName("dishTypes")
    val dishTypes: List<String>,
    @SerializedName("servings")
    val servings: Int,
    @SerializedName("readyInMinutes")
    val readyInMinutes: Int
)

fun RecipeDetailsResponse.toRecipeDetailsModel(): RecipeDetails {

    val ingredients = extendedIngredients.map {
        it.toExtendedIngredientModal()
    }

    return RecipeDetails(
        id = id,
        extendedIngredients = ingredients,
        dishTypes = dishTypes,
        servings = servings,
        readyInMinutes = readyInMinutes
    )
}
