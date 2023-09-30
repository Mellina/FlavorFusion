package com.bassul.flavorfusion.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import com.bassul.core.domain.model.RecipeDetails
import com.bassul.core.usecase.GetDetailsRecipeUseCase
import com.bassul.flavorfusion.presentation.extensions.watchStatus
import kotlin.coroutines.CoroutineContext

class UiActionStateLiveData(
    private val coroutineContext: CoroutineContext,
    private val getDetailsRecipeUseCase: GetDetailsRecipeUseCase
) {

    private val action = MutableLiveData<Action>()
    val state: LiveData<UiState> = action.switchMap {
        liveData(coroutineContext) {
            when (it) {
                is Action.Load -> {
                    getDetailsRecipeUseCase(
                        GetDetailsRecipeUseCase.GetDetailsRecipeParams(it.recipeId)
                    ).watchStatus(
                        loading = {
                            emit(UiState.Loading)
                        },
                        success = { data ->
                            emit(UiState.Success(data))
                        },
                        error = { throwable ->
                            emit(UiState.Error(throwable))
                        })
                }
            }
        }
    }

    fun load(recipeId: Long) {
        action.value = Action.Load(recipeId)
    }

    sealed class UiState {
        object Loading : UiState()
        data class Success(val detailsRecipe: RecipeDetails) : UiState()
        data class Error(val error: Throwable) : UiState()

    }

    sealed class Action {
        data class Load(val recipeId: Long) : Action()
    }
}