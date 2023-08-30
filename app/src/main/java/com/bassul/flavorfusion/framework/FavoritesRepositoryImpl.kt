package com.bassul.flavorfusion.framework

import com.bassul.core.data.repository.FavoriteLocalDataSource
import com.bassul.core.data.repository.FavoritesRepository
import com.bassul.core.domain.model.Recipe
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FavoritesRepositoryImpl @Inject constructor(
    private val favoriteLocalDataSource: FavoriteLocalDataSource
) : FavoritesRepository {
    override fun getAll(): Flow<List<Recipe>> {
        return favoriteLocalDataSource.getAll()
    }

    override suspend fun saveFavorite(recipe: Recipe) {
        return favoriteLocalDataSource.save(recipe = recipe)
    }

    override suspend fun deleteFavorite(recipe: Recipe) {
        return favoriteLocalDataSource.delete(recipe = recipe)
    }
}