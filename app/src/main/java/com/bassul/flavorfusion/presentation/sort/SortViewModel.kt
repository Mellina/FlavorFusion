package com.bassul.flavorfusion.presentation.sort

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import com.bassul.core.usecase.GetRecipesSortingUseCase
import com.bassul.core.usecase.SaveRecipesSortingUseCase
import com.bassul.core.usecase.base.CoroutinesDispatchers
import com.bassul.flavorfusion.presentation.extensions.watchStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@HiltViewModel
class SortViewModel @Inject constructor(
    private val getRecipesSortingUseCase: GetRecipesSortingUseCase,
    private val saveRecipesSortingUseCase: SaveRecipesSortingUseCase,
    private val coroutinesDispatchers: CoroutinesDispatchers
) : ViewModel() {

    private val action = MutableLiveData<Action>()
    val state: LiveData<UiState> = action.switchMap { action ->
        liveData(coroutinesDispatchers.main()) {
            when (action) {
                Action.GetStoredSorting -> {
                    getRecipesSortingUseCase.invoke()
                        .collect { sorting ->
                            emit(UiState.SortingResult(sorting))
                        }
                }

                is Action.ApplySorting -> {
                    val order = action.order
                    val orderBy = action.orderBy

                    saveRecipesSortingUseCase.invoke(
                        SaveRecipesSortingUseCase.Params(order to orderBy)
                    ).watchStatus(
                        loading = {
                            emit(UiState.ApplyState.Loading)
                        },
                        success = {
                            emit(UiState.ApplyState.Success)
                        },
                        error = {
                            emit(UiState.ApplyState.Error)
                        }

                    )
                }
            }
        }
    }

    init {
        action.value = Action.GetStoredSorting
    }

    fun applySorting(orderBy: String, order: String) {
        action.value = Action.ApplySorting(orderBy = orderBy, order = order)
    }

    sealed class UiState {
        data class SortingResult(val storedSorting: Pair<String, String>) : UiState()

        sealed class ApplyState : UiState() {
            object Loading : ApplyState()
            object Success : ApplyState()
            object Error : ApplyState()
        }
    }

    sealed class Action {
        object GetStoredSorting : Action()
        data class ApplySorting(
            val order: String,
            val orderBy: String
        ) : Action()
    }
}