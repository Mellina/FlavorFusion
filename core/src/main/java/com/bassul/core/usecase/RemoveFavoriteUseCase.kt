package com.bassul.core.usecase

import com.bassul.core.data.repository.FavoritesRepository
import com.bassul.core.domain.model.Recipe
import com.bassul.core.usecase.base.CoroutinesDispatchers
import com.bassul.core.usecase.base.ResultStatus
import com.bassul.core.usecase.base.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface RemoveFavoriteUseCase {

    operator fun invoke(params: Params): Flow<ResultStatus<Unit>>

    data class Params(val recipeId: Long, val title: String, val imageUrl: String)
}

class RemoveFavoriteUseCaseImpl @Inject constructor(
    private val repository: FavoritesRepository,
    private val dispatchers: CoroutinesDispatchers
) : UseCase<RemoveFavoriteUseCase.Params, Unit>(), RemoveFavoriteUseCase {
    override suspend fun doWork(params: RemoveFavoriteUseCase.Params): ResultStatus<Unit> {
        return withContext(dispatchers.io()) {
            repository.deleteFavorite(
                Recipe(params.recipeId, params.title, params.imageUrl)
            )
            ResultStatus.Success(Unit)
        }
    }
}