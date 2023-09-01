package com.bassul.flavorfusion.presentation.detail

import androidx.lifecycle.ViewModel
import com.bassul.core.usecase.AddFavoriteUseCase
import com.bassul.core.usecase.CheckFavoriteUseCase
import com.bassul.core.usecase.GetDetailsRecipeUseCase
import com.bassul.core.usecase.base.CoroutinesDispatchers
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    getDetailsRecipeUseCase: GetDetailsRecipeUseCase,
    checkFavoriteUseCase: CheckFavoriteUseCase,
    addFavoriteUseCase: AddFavoriteUseCase,
    coroutinesDispatchers: CoroutinesDispatchers
) : ViewModel() {

    val details = UiActionStateLiveData(
        coroutinesDispatchers.main(),
        getDetailsRecipeUseCase
    )

    val favorite = FavoriteUiActionStateLiveData(
        coroutinesDispatchers.main(),
        checkFavoriteUseCase,
        addFavoriteUseCase,
    )

}