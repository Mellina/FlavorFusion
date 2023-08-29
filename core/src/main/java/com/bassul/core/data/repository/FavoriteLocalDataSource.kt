package com.bassul.core.data.repository

import com.bassul.core.domain.model.Recipe
import kotlinx.coroutines.flow.Flow

interface FavoriteLocalDataSource {

    suspend fun getAll(): Flow<List<Recipe>>
    suspend fun save(recipe: Recipe)
    suspend fun delete(recipe: Recipe)
}