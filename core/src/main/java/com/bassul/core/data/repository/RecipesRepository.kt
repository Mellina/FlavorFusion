package com.bassul.core.data.repository

import androidx.paging.PagingSource
import com.bassul.core.domain.model.Recipe
import com.bassul.core.domain.model.RecipeDetails

interface RecipesRepository {

    fun getRecipes(query: String): PagingSource<Int, Recipe>

    suspend fun getDetailsRecipe(recipeId: Long): RecipeDetails
}