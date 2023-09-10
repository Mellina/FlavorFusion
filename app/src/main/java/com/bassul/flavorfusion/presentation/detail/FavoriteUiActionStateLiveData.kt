package com.bassul.flavorfusion.presentation.detail

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataScope
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import com.bassul.core.usecase.AddFavoriteUseCase
import com.bassul.core.usecase.CheckFavoriteUseCase
import com.bassul.core.usecase.RemoveFavoriteUseCase
import com.bassul.flavorfusion.R
import com.bassul.flavorfusion.presentation.extensions.watchStatus
import kotlin.coroutines.CoroutineContext

class FavoriteUiActionStateLiveData(
    private val coroutineContext: CoroutineContext,
    private val checkFavoriteUseCase: CheckFavoriteUseCase,
    private val addFavoriteUseCase: AddFavoriteUseCase,
    private val removeFavoriteUseCase: RemoveFavoriteUseCase,
) {

    @set:VisibleForTesting
    var currentFavoriteIcon = R.drawable.ic_favorite_unchecked

    private val action = MutableLiveData<Action>()
    val state: LiveData<UiState> = action.switchMap {
        liveData(coroutineContext) {
            when (it) {
                is Action.CheckFavorite -> {
                    checkFavoriteUseCase.invoke(CheckFavoriteUseCase.Params(it.recipeId))
                        .watchStatus(
                            success = { isFavorite ->
                                if (isFavorite) {
                                    currentFavoriteIcon = R.drawable.ic_favorite_checked
                                }
                                emitFavoriteIcon()
                            },
                            error = {}
                        )


                }

                is Action.AddFavorite -> {
                    it.detailViewArg.run {
                        addFavoriteUseCase.invoke(
                            AddFavoriteUseCase.Params(id, name, imageUrl)
                        ).watchStatus(
                            loading = {
                                emit(UiState.Loading)
                            },
                            success = {
                                currentFavoriteIcon = R.drawable.ic_favorite_checked
                                emitFavoriteIcon()

                            },
                            error = {
                                //emit(UiState.Error) preciso criar a string
                            }
                        )
                    }
                }

                is Action.RemoveFavorite -> {
                    it.detailViewArg.run {
                        removeFavoriteUseCase.invoke(
                            RemoveFavoriteUseCase.Params(recipeId = id, title = name, imageUrl = imageUrl)
                        ).watchStatus(
                            loading = {
                               emit(UiState.Loading)
                            },
                            success =  {
                                currentFavoriteIcon = R.drawable.ic_favorite_unchecked
                                emitFavoriteIcon()
                            },
                            error = {
                                //Add erro depois, criar mensagem de erro depois
                            }
                        )
                    }
                }
            }
        }
    }

    private suspend fun LiveDataScope<UiState>.emitFavoriteIcon() {
        emit(UiState.Icon(currentFavoriteIcon))
    }

    fun checkFavorite(recipeId: Long) {
        action.value = Action.CheckFavorite(recipeId)
    }

    fun update(detailViewArg: DetailViewArg) {
        action.value = if (currentFavoriteIcon == R.drawable.ic_favorite_unchecked) {
            Action.AddFavorite(detailViewArg)
        } else Action.RemoveFavorite(detailViewArg)
    }

    sealed class UiState {
        object Loading : UiState()
        data class Icon(@DrawableRes val icon: Int) : UiState()
        data class Error(@StringRes val messageResId: Int) : UiState()
    }

    sealed class Action {
        data class CheckFavorite(val recipeId: Long) : Action()
        data class AddFavorite(val detailViewArg: DetailViewArg) : Action()
        data class RemoveFavorite(val detailViewArg: DetailViewArg) : Action()
    }
}