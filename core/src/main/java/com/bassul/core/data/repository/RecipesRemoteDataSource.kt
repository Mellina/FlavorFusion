package com.bassul.core.data.repository

interface RecipesRemoteDataSource<T> {

    suspend fun fetchRecipes(queries: Map<String, String>) : T
}