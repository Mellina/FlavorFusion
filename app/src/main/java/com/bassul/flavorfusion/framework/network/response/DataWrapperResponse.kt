package com.bassul.flavorfusion.framework.network.response

import com.google.gson.annotations.SerializedName

data class DataWrapperResponse(
    @SerializedName("results")
    val results: List<RecipeResponse>,
    @SerializedName("offset")
    val offset: Int,
    @SerializedName("totalResults")
    val totalResults: Int
)
