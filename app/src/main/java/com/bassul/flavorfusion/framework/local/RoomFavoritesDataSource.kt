package com.bassul.flavorfusion.framework.local

import com.bassul.core.data.repository.FavoriteLocalDataSource
import com.bassul.core.domain.model.Recipe
import com.bassul.flavorfusion.framework.db.dao.FavoriteDao
import com.bassul.flavorfusion.framework.db.entity.FavoriteEntity
import com.bassul.flavorfusion.framework.db.entity.toRecipesModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RoomFavoritesDataSource @Inject constructor(
    private val favoriteDao: FavoriteDao
) : FavoriteLocalDataSource {
    override fun getAll(): Flow<List<Recipe>> {
        return favoriteDao.loadFavorites().map {
            it.toRecipesModel()
        }
    }

    override suspend fun save(recipe: Recipe) {
        favoriteDao.insertFavorite(recipe.toRecipeEntity())
    }

    override suspend fun delete(recipe: Recipe) {
        favoriteDao.deleteFavorite(recipe.toRecipeEntity())
    }

    private fun Recipe.toRecipeEntity() = FavoriteEntity(id, title, image)
}