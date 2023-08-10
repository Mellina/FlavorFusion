package com.bassul.flavorfusion.framework

import androidx.paging.PagingSource
import com.bassul.core.data.repository.RecipesRemoteDataSource
import com.bassul.core.data.repository.RecipesRepository
import com.bassul.core.domain.model.Recipe
import com.bassul.core.domain.model.RecipeDetails
import com.bassul.flavorfusion.framework.paging.RecipesPagingSource
import javax.inject.Inject

class RecipesRepositoryImpl @Inject constructor(
    private val remoteDataSource: RecipesRemoteDataSource
): RecipesRepository {

    override fun getRecipes(query: String): PagingSource<Int, Recipe> {
        return RecipesPagingSource(remoteDataSource, query)
    }

    override suspend fun getDetailsRecipe(recipeId: Long): RecipeDetails {
        return remoteDataSource.fetchRecipeDetails(recipeId)
    }


}