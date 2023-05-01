package com.bassul.core.data.network

import com.bassul.core.data.network.response.DataWrapperResponse
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface FoodApi {

    @GET("complexSearch?query=pasta")
    suspend fun getRecipes(
        @QueryMap
        queries: Map<String, String>
    ): DataWrapperResponse

}