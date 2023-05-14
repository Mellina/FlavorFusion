package com.bassul.flavorfusion.framework

import androidx.paging.PagingSource
import com.bassul.core.data.repository.RecipesRemoteDataSource
import com.bassul.core.data.repository.RecipesRepository
import com.bassul.core.domain.model.Recipe
import com.bassul.flavorfusion.framework.network.response.DataWrapperResponse
import com.bassul.flavorfusion.framework.paging.RecipesPagingSource
import javax.inject.Inject

class RecipesRepositoryImpl @Inject constructor(
    private val remoteDataSource: RecipesRemoteDataSource<DataWrapperResponse>
): RecipesRepository {

    override fun getRecipes(query: String): PagingSource<Int, Recipe> {
        return RecipesPagingSource(remoteDataSource, query)
    }
}