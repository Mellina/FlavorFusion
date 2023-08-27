package com.bassul.core.usecase

import com.bassul.core.data.repository.RecipesRepository
import com.bassul.core.domain.model.RecipeDetails
import com.bassul.core.usecase.base.ResultStatus
import com.bassul.core.usecase.base.UseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface GetDetailsRecipeUseCase {

    operator fun invoke(params: GetDetailsRecipeParams): Flow<ResultStatus<RecipeDetails>>
    data class GetDetailsRecipeParams(val recipeId: Long)
}

class GetDetailsRecipeUseCaseImpl @Inject constructor(
    private val repository: RecipesRepository
) : GetDetailsRecipeUseCase,
    UseCase<GetDetailsRecipeUseCase.GetDetailsRecipeParams, RecipeDetails>() {

    override suspend fun doWork(
        params: GetDetailsRecipeUseCase.GetDetailsRecipeParams
    ): ResultStatus<RecipeDetails> {
        val recipe = repository.getDetailsRecipe(params.recipeId)
        return ResultStatus.Success(recipe)
    }

}