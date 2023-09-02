package com.bassul.core.usecase

import com.bassul.core.data.repository.FavoritesRepository
import com.bassul.core.domain.model.Recipe
import com.bassul.core.usecase.base.CoroutinesDispatchers
import com.bassul.core.usecase.base.FlowUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface GetFavoritesUseCase {
    suspend operator fun invoke(params: Unit = Unit): Flow<List<Recipe>>
}

class GetFavoritesUseCaseImpl @Inject constructor(
    private val favoritesRepository: FavoritesRepository,
    private val dispatchers: CoroutinesDispatchers
) : FlowUseCase<Unit, List<Recipe>>(), GetFavoritesUseCase {

    override suspend fun createFlowObservable(params: Unit): Flow<List<Recipe>> {
        return withContext(dispatchers.io()) {
            favoritesRepository.getAll()
        }
    }
}