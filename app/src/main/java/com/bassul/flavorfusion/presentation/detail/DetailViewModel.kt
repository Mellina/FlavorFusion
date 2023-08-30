package com.bassul.flavorfusion.presentation.detail

import androidx.annotation.DrawableRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bassul.core.domain.model.RecipeDetails
import com.bassul.core.usecase.AddFavoriteUseCase
import com.bassul.core.usecase.GetDetailsRecipeUseCase
import com.bassul.core.usecase.base.ResultStatus
import com.bassul.flavorfusion.R
import com.bassul.flavorfusion.presentation.extensions.watchStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getDetailsRecipeUseCase: GetDetailsRecipeUseCase,
    private val addFavoriteUseCase: AddFavoriteUseCase
) : ViewModel() {

    private val _uiState = MutableLiveData<UiState>()
    val uiState: LiveData<UiState> get() = _uiState

    private val _favoriteUiState = MutableLiveData<FavoriteUiSate>()
    val favoriteUiState: LiveData<FavoriteUiSate> get() = _favoriteUiState

    init {
        _favoriteUiState.value =
            FavoriteUiSate.FavoriteIcon(com.google.android.material.R.drawable.ic_clock_black_24dp)
    }

    fun getDetailsRecipe(recipeId: Long) = viewModelScope.launch {
        getDetailsRecipeUseCase(GetDetailsRecipeUseCase.GetDetailsRecipeParams(recipeId))
            .watchStatus(
                loading = {
                    _uiState.value = UiState.Loading
                },
                success = { data ->
                    _uiState.value =UiState.Success(data)
                },
                error = { throwable ->
                    _uiState.value =UiState.Error(throwable)
                }

            )
    }

    fun updateFavorite(detailViewArg: DetailViewArg) = viewModelScope.launch {
        detailViewArg.run {
            addFavoriteUseCase.invoke(
                AddFavoriteUseCase.Params(id, name, imageUrl)
            ).watchStatus(
                loading = {
                    _favoriteUiState.value = FavoriteUiSate.Loading
                },
                success = {
                    _favoriteUiState.value =
                        FavoriteUiSate.FavoriteIcon(androidx.appcompat.R.drawable.abc_btn_check_material)
                },
                error = {

                }
            )
        }
    }

    sealed class UiState {
        object Loading : UiState()
        data class Success(val detailsRecipe: RecipeDetails) : UiState()
        data class Error(val error: Throwable) : UiState()

    }
    sealed class FavoriteUiSate {
        object Loading : FavoriteUiSate()
        class FavoriteIcon(@DrawableRes val icon: Int) : FavoriteUiSate()
    }
}