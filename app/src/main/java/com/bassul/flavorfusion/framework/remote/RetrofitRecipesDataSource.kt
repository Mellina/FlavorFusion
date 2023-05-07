package com.bassul.flavorfusion.framework.remote

import com.bassul.core.data.repository.RecipesRemoteDataSource
import com.bassul.flavorfusion.framework.network.FoodApi
import com.bassul.flavorfusion.framework.network.response.DataWrapperResponse
import javax.inject.Inject

class RetrofitRecipesDataSource @Inject constructor(
    private val foodApi: FoodApi
) : RecipesRemoteDataSource<DataWrapperResponse>{
    override suspend fun fetchRecipes(queries: Map<String, String>): DataWrapperResponse {
        return foodApi.getRecipes(queries)
    }
}