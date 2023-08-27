package com.bassul.flavorfusion.framework.network.response

import com.bassul.core.domain.model.ExtendedIngredient
import com.google.gson.annotations.SerializedName

data class ExtendedIngredientResponse(
    @SerializedName("name")
    val name: String,
    @SerializedName("amount")
    val amount: Double,
    @SerializedName("unit")
    val unit: String,
    @SerializedName("image")
    val image: String
)

fun ExtendedIngredientResponse.toExtendedIngredientModal(): ExtendedIngredient {
    return ExtendedIngredient(
        name = name,
        amount = amount,
        unit = unit,
        image = image
    )
}
