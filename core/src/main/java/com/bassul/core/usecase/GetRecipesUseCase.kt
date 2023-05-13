package com.bassul.core.usecase

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.bassul.core.data.repository.RecipesRepository
import com.bassul.core.domain.model.Recipe
import com.bassul.core.usecase.base.PagingUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRecipesUseCase @Inject constructor(
    private val recipesRepository: RecipesRepository
) : PagingUseCase<GetRecipesUseCase.GetRecipesParams, Recipe>() {

    override fun createFlowObservable(params: GetRecipesParams): Flow<PagingData<Recipe>> {
        return Pager(config = params.pagingConfig) {
            recipesRepository.getRecipes(params.query)
        }.flow
    }

    data class GetRecipesParams(val query: String, val pagingConfig: PagingConfig)
}