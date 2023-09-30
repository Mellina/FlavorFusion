package com.bassul.flavorfusion.framework.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.bassul.core.data.repository.RecipesRemoteDataSource
import com.bassul.flavorfusion.framework.db.AppDatabase
import com.bassul.flavorfusion.framework.db.entity.RecipeEntity
import com.bassul.flavorfusion.framework.db.entity.RemoteKey
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class RecipesRemoteMediator @Inject constructor(
    private val query: String,
    private val order: String,
    private val orderBy: String,
    private val database: AppDatabase,
    private val remoteDataSource: RecipesRemoteDataSource
) : RemoteMediator<Int, RecipeEntity>() {

    private val recipeDao = database.recipeDao()
    private val remoteKeyDao = database.remoteKeyDao()

    @Suppress("ReturnCount")
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, RecipeEntity>
    ): MediatorResult {
        return try {

            val offset = when (loadType) {
                LoadType.REFRESH -> 0
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val remoteKey = database.withTransaction {
                        remoteKeyDao.remoteKey()
                    }

                    if (remoteKey.nextOffset == null) {
                        return MediatorResult.Success(endOfPaginationReached = true)
                    }

                    remoteKey.nextOffset
                }
            }


            val queries = hashMapOf(
                "offset" to offset.toString()
            )

            if (query.isNotEmpty()) {
                queries["query"] = query
            }

            if (order.isNotEmpty()) {
                queries["sortDirection"] = order
            }

            if (orderBy.isNotEmpty()) {
                queries["sort"] = orderBy
            }

            val recipePaging = remoteDataSource.fetchRecipes(queries)

            val responseOffset = recipePaging.offset
            val totalRecipes = recipePaging.totalResults

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    remoteKeyDao.clearAll()
                    recipeDao.clearAll()
                }

                remoteKeyDao.insertOrReplace(
                    RemoteKey(nextOffset = responseOffset + state.config.pageSize)
                )

                val recipesEntities = recipePaging.recipes.map {
                    RecipeEntity(
                        id = it.id,
                        name = it.title,
                        imageUrl = it.image
                    )
                }

                recipeDao.insertAll(recipesEntities)
            }

            MediatorResult.Success(endOfPaginationReached = responseOffset >= totalRecipes)
        } catch (e: IOException) {
            MediatorResult.Error(e)

        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }

}