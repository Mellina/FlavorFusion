package com.bassul.flavorfusion.framework.network.response

data class DataWrapperResponse(
    val recipes: List<RecipeResponse>,
    val offset: Int,
    val totalResults: Int
)
