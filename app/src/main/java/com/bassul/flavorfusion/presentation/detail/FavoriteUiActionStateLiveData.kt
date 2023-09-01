package com.bassul.flavorfusion.presentation.detail

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import com.bassul.core.usecase.AddFavoriteUseCase
import com.bassul.core.usecase.CheckFavoriteUseCase
import com.bassul.flavorfusion.R
import com.bassul.flavorfusion.presentation.extensions.watchStatus
import kotlin.coroutines.CoroutineContext

class FavoriteUiActionStateLiveData(
    private val coroutineContext: CoroutineContext,
    private val checkFavoriteUseCase: CheckFavoriteUseCase,
    private val addFavoriteUseCase: AddFavoriteUseCase,
) {

    private val action = MutableLiveData<Action>()
    val state: LiveData<UiState> = action.switchMap {
        liveData(coroutineContext) {
            when (it) {
                is Action.CheckFavorite -> {
                    checkFavoriteUseCase.invoke(CheckFavoriteUseCase.Params(it.recipeId))
                        .watchStatus(
                            success = { isFavorite ->
                                var icon = R.drawable.ic_favorite_unchecked
                                if (isFavorite) {
                                    icon = R.drawable.ic_favorite_checked
                                }
                                emit(UiState.Icon(icon))
                            },
                            error = {

                            }
                        )


                }

                is Action.Update -> {
                    it.detailViewArg.run {
                        addFavoriteUseCase.invoke(
                            AddFavoriteUseCase.Params(id, name, imageUrl)
                        ).watchStatus(
                            loading = {
                                emit(UiState.Loading)
                            },
                            success = {
                                emit(UiState.Icon(androidx.appcompat.R.drawable.abc_btn_check_material))

                            },
                            error = {
                                //emit(UiState.Error) preciso criar a string
                            }
                        )
                    }
                }

            }
        }
    }

    fun checkFavorite(recipeId: Long) {
        action.value = Action.CheckFavorite(recipeId)
    }

    fun update(detailViewArg: DetailViewArg) {
        action.value = Action.Update(detailViewArg)
    }

    sealed class UiState {
        object Loading : UiState()
        data class Icon(@DrawableRes val icon: Int) : UiState()
        data class Error(@StringRes val messageResId: Int) : UiState()
    }

    sealed class Action {
        data class CheckFavorite(val recipeId: Long) : Action()
        data class Update(val detailViewArg: DetailViewArg) : Action()
    }
}