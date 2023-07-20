package com.bassul.core.data.repository

import com.bassul.core.domain.model.RecipePaging

interface RecipesRemoteDataSource {

    suspend fun fetchRecipes(queries: Map<String, String>) : RecipePaging
}