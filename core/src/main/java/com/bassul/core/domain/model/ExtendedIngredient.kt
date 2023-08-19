package com.bassul.core.domain.model

data class ExtendedIngredient(
    val name: String,
    val amount: Double,
    val unit: String,
    val image: String
) {

    fun getImageUrl() = startImageUrl + image

    companion object {
        private const val startImageUrl = "https://spoonacular.com/cdn/ingredients_100x100/"
    }
}
