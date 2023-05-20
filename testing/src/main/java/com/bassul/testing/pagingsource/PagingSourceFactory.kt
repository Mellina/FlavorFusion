package com.bassul.testing.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.bassul.core.domain.model.Recipe

class PagingSourceFactory {

    fun create(recipe: List<Recipe>) = object : PagingSource<Int, Recipe>() {
        override fun getRefreshKey(state: PagingState<Int, Recipe>) = 1

        override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Recipe> {
            return LoadResult.Page(
                data = recipe,
                prevKey = null,
                nextKey = 2
            )
        }
    }
}