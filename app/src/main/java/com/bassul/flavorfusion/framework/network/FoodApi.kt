package com.bassul.flavorfusion.framework.network

import com.bassul.flavorfusion.framework.network.response.DataWrapperResponse
import com.bassul.flavorfusion.framework.network.response.RecipeDetailsResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface FoodApi {

    @GET("complexSearch")
    suspend fun getRecipes(
        @QueryMap
        queries: Map<String, String>
    ): DataWrapperResponse

    @GET("/recipes/{id}/information")
    suspend fun getRecipeDetails(
        @Path("id")
        id: Long
    ): RecipeDetailsResponse
}