package com.bassul.flavorfusion.framework

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.map
import com.bassul.core.data.repository.RecipesRemoteDataSource
import com.bassul.core.data.repository.RecipesRepository
import com.bassul.core.domain.model.Recipe
import com.bassul.core.domain.model.RecipeDetails
import com.bassul.flavorfusion.framework.db.AppDatabase
import com.bassul.flavorfusion.framework.paging.RecipesPagingSource
import com.bassul.flavorfusion.framework.paging.RecipesRemoteMediator
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class RecipesRepositoryImpl @Inject constructor(
    private val remoteDataSource: RecipesRemoteDataSource,
    private val database: AppDatabase
) : RecipesRepository {

    override fun getRecipes(query: String): PagingSource<Int, Recipe> {
        return RecipesPagingSource(remoteDataSource, query)
    }


    override fun getCachedRecipes(
        query: String,
        pagingConfig: PagingConfig
    ): Flow<PagingData<Recipe>> {
        return Pager(
            config = pagingConfig,
            remoteMediator = RecipesRemoteMediator(query, database = database, remoteDataSource)
        ) {
            database.recipeDao().pagingSource()
        }.flow.map { pagingData ->
            pagingData.map {
                Recipe(
                    it.id,
                    it.name,
                    it.imageUrl
                )
            }

        }
    }

    override suspend fun getDetailsRecipe(recipeId: Long): RecipeDetails {
        return remoteDataSource.fetchRecipeDetails(recipeId)
    }


}