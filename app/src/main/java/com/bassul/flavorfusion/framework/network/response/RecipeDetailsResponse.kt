package com.bassul.flavorfusion.framework.network.response

import com.bassul.core.domain.model.RecipeDetails
import com.google.gson.annotations.SerializedName

data class RecipeDetailsResponse(
    @SerializedName("id")
    val id: Long,
    @SerializedName("extendedIngredients")
    val extendedIngredients: ExtendedIngredientResponse,
    @SerializedName("dishTypes")
    val dishTypes: List<String>,
    @SerializedName("servings")
    val servings: Int,
    @SerializedName("readyInMinutes")
    val readyInMinutes: Int
)

fun RecipeDetailsResponse.toRecipeDetailsModel(): RecipeDetails {
    return RecipeDetails(
        id = id,
        extendedIngredients = extendedIngredients.toExtendedIngredientModal(),
        dishTypes = dishTypes,
        servings = servings,
        readyInMinutes = readyInMinutes
    )
}
