package com.bassul.core.data.repository

import com.bassul.core.domain.model.Recipe
import kotlinx.coroutines.flow.Flow

interface FavoritesRepository {

    fun getAll(): Flow<List<Recipe>>
    suspend fun isFavorite(recipeId: Long): Boolean
    suspend fun saveFavorite(recipe: Recipe)
    suspend fun deleteFavorite(recipe: Recipe)

}