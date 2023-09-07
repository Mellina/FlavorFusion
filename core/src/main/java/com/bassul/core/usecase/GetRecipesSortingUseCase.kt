package com.bassul.core.usecase

import com.bassul.core.data.repository.FavoritesRepository
import com.bassul.core.data.repository.StorageConstants
import com.bassul.core.data.repository.StorageRepository
import com.bassul.core.domain.model.Recipe
import com.bassul.core.usecase.base.CoroutinesDispatchers
import com.bassul.core.usecase.base.FlowUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface GetRecipesSortingUseCase {
    suspend operator fun invoke(params: Unit = Unit): Flow<String>
}

class GetRecipesSortingUseCaseImpl @Inject constructor(
    private val storageRepository: StorageRepository,
    private val dispatchers: CoroutinesDispatchers
) : FlowUseCase<Unit, String>(), GetRecipesSortingUseCase {

    override suspend fun createFlowObservable(params: Unit): Flow<String> {
        return withContext(dispatchers.io()) {
            storageRepository.sorting

                /*.map { sorting ->
                when (sorting) {
                    StorageConstants.ORDER_ASCENDING ->
                        StorageConstants.ORDER_ASCENDING

                    StorageConstants.ORDER_DESCENDING ->
                        StorageConstants.ORDER_DESCENDING

                    else -> ""
                }

            }*/
        }
    }
}