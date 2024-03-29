package com.bassul.core.data.repository

import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.bassul.core.domain.model.Recipe
import com.bassul.core.domain.model.RecipeDetails
import kotlinx.coroutines.flow.Flow

interface RecipesRepository {

    fun getCachedRecipes(query: String,
                         order: String,
                         orderBy: String,
                         pagingConfig: PagingConfig): Flow<PagingData<Recipe>>

    suspend fun getDetailsRecipe(recipeId: Long): RecipeDetails
}