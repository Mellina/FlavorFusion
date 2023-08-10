package com.bassul.flavorfusion.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bassul.core.domain.model.RecipeDetails
import com.bassul.core.usecase.GetDetailsRecipeUseCase
import com.bassul.core.usecase.base.ResultStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getDetailsRecipeUseCase: GetDetailsRecipeUseCase
) : ViewModel() {

    private val _uiState = MutableLiveData<UiState>()
    val uiState: LiveData<UiState> get() = _uiState

    fun getDetailsRecipe(recipeId: Long) = viewModelScope.launch {
        getDetailsRecipeUseCase(GetDetailsRecipeUseCase.GetDetailsRecipeParams(recipeId))
            .watchStatus()
    }

    private fun Flow<ResultStatus<RecipeDetails>>.watchStatus() = viewModelScope.launch {
        collect { status ->
           _uiState.value = when (status) {
                ResultStatus.Loading -> UiState.Loading
                is ResultStatus.Success -> UiState.Success(status.data)
                is ResultStatus.Error -> UiState.Error(status.throwable)
            }
        }
    }

    sealed class UiState {
        object Loading : UiState()
        data class Success(val detailsRecipe: RecipeDetails) : UiState()
        data class Error(val error: Throwable): UiState()

    }
}