package com.bassul.core.usecase

import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.bassul.core.data.repository.RecipesRepository
import com.bassul.core.data.repository.StorageRepository
import com.bassul.core.domain.model.Recipe
import com.bassul.core.usecase.GetRecipesUseCase.GetRecipesParams
import com.bassul.core.usecase.base.PagingUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import javax.inject.Inject


interface GetRecipesUseCase {
    operator fun invoke(params: GetRecipesParams): Flow<PagingData<Recipe>>
    data class GetRecipesParams(val query: String, val pagingConfig: PagingConfig)
}

class GetRecipesUseCaseImpl @Inject constructor(
    private val recipesRepository: RecipesRepository,
    private val storageRepository: StorageRepository
) : GetRecipesUseCase, PagingUseCase<GetRecipesParams, Recipe>() {

    override fun createFlowObservable(params: GetRecipesParams): Flow<PagingData<Recipe>> {
        val order = runBlocking { storageRepository.sorting.first() }
        val orderBy = runBlocking { storageRepository.sortingBy.first() }

        return recipesRepository.getCachedRecipes(params.query, order, orderBy, params.pagingConfig)
    }


}