package com.bassul.core.usecase

import com.bassul.core.data.repository.FavoritesRepository
import com.bassul.core.usecase.base.CoroutinesDispatchers
import com.bassul.core.usecase.base.ResultStatus
import com.bassul.core.usecase.base.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface CheckFavoriteUseCase {

    operator fun invoke(params: Params): Flow<ResultStatus<Boolean>>

    data class Params(val recipeId: Long)
}

class CheckFavoriteUseCaseImpl @Inject constructor(
    private val repository: FavoritesRepository,
    private val dispatchers: CoroutinesDispatchers
) : UseCase<CheckFavoriteUseCase.Params, Boolean>(), CheckFavoriteUseCase {
    override suspend fun doWork(params: CheckFavoriteUseCase.Params): ResultStatus<Boolean> {
        return withContext(dispatchers.io()) {
            val isFavorite = repository.isFavorite(params.recipeId)
            ResultStatus.Success(isFavorite)
        }
    }

}