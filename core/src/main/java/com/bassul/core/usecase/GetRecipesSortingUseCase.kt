package com.bassul.core.usecase

import com.bassul.core.data.repository.StorageRepository
import com.bassul.core.usecase.base.CoroutinesDispatchers
import com.bassul.core.usecase.base.FlowUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface GetRecipesSortingUseCase {
    suspend operator fun invoke(params: Unit = Unit): Flow<Pair<String, String>>
}

class GetRecipesSortingUseCaseImpl @Inject constructor(
    private val storageRepository: StorageRepository,
    private val dispatchers: CoroutinesDispatchers
) : FlowUseCase<Unit, Pair<String, String>>(), GetRecipesSortingUseCase {

    override suspend fun createFlowObservable(params: Unit): Flow<Pair<String, String>> {
        return withContext(dispatchers.io()) {
            storageRepository.sorting.combine(storageRepository.sortingBy) { sorting, sortingBy ->
                sorting to sortingBy
            }
        }
    }
}