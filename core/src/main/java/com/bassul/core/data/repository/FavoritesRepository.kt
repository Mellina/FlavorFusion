package com.bassul.core.data.repository

import com.bassul.core.domain.model.Recipe
import kotlinx.coroutines.flow.Flow

interface FavoritesRepository {
    suspend fun getAll(): Flow<List<Recipe>>
    suspend fun saveFavorite(recipe: Recipe)
    suspend fun deleteFavorite(recipe: Recipe)

}