package com.bassul.flavorfusion.framework.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.bassul.core.data.repository.RecipesRemoteDataSource
import com.bassul.core.domain.model.Recipe
import com.bassul.flavorfusion.framework.network.response.DataWrapperResponse
import com.bassul.flavorfusion.framework.network.response.toRecipeModel

class RecipesPagingSource(
    private val remoteDataSource: RecipesRemoteDataSource<DataWrapperResponse>,
    private val query: String
) : PagingSource<Int, Recipe>() {

    @Suppress("TooGenericExceptionCaught")
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Recipe> {
        return try {
            val offset = params.key ?: 0

            val queries = hashMapOf(
                "offset" to offset.toString()
            )

            if (query.isNotEmpty()) {
                queries["query"] = query
            }

            val response = remoteDataSource.fetchRecipes(queries)

            val responseOffset = response.offset
            val totalRecipes = response.totalResults

            LoadResult.Page(
                data = response.recipes.map {
                    it.toRecipeModel()
                },
                null,
                nextKey = if (offset < totalRecipes) {
                    responseOffset + LIMIT
                } else null
            )

        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Recipe>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }


    companion object {
        private const val LIMIT = 2
    }
}