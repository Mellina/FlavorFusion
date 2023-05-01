package com.bassul.core.data.repository

import androidx.paging.PagingSource
import com.bassul.core.domain.model.Recipe

interface RecipesRepository {

    fun getRecipes(query: String): PagingSource<Int, Recipe>
}