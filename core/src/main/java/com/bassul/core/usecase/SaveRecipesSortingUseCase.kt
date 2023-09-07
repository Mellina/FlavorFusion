package com.bassul.core.usecase

import com.bassul.core.data.repository.FavoritesRepository
import com.bassul.core.data.repository.StorageRepository
import com.bassul.core.domain.model.Recipe
import com.bassul.core.usecase.base.CoroutinesDispatchers
import com.bassul.core.usecase.base.ResultStatus
import com.bassul.core.usecase.base.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface SaveRecipesSortingUseCase {

    operator fun invoke(params: Params): Flow<ResultStatus<Unit>>
    data class Params(val sorting: String)
}

class SaveRecipesSortingUseCaseImpl @Inject constructor(
    private val repository: StorageRepository,
    private val dispatchers: CoroutinesDispatchers
) : UseCase<SaveRecipesSortingUseCase.Params, Unit>(), SaveRecipesSortingUseCase {
    override suspend fun doWork(params: SaveRecipesSortingUseCase.Params): ResultStatus<Unit> {
        return withContext(dispatchers.io()) {
            repository.saveSorting(
                params.sorting
            )
            ResultStatus.Success(Unit)
        }
    }
}