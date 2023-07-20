package com.bassul.flavorfusion.framework.remote

import com.bassul.core.data.repository.RecipesRemoteDataSource
import com.bassul.core.domain.model.RecipePaging
import com.bassul.flavorfusion.framework.network.FoodApi
import com.bassul.flavorfusion.framework.network.response.DataWrapperResponse
import com.bassul.flavorfusion.framework.network.response.toRecipeModel
import javax.inject.Inject

class RetrofitRecipesDataSource @Inject constructor(
    private val foodApi: FoodApi
) : RecipesRemoteDataSource {
    override suspend fun fetchRecipes(queries: Map<String, String>): RecipePaging {

        val data = foodApi.getRecipes(queries)
        val recipes = data.results.map {
            it.toRecipeModel()
        }

        return RecipePaging(
            data.offset,
            data.totalResults,
            recipes
        )
    }
}