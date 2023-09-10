package com.bassul.flavorfusion.presentation.recipes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.bassul.core.domain.model.Recipe
import com.bassul.core.usecase.GetRecipesUseCase
import com.bassul.core.usecase.base.CoroutinesDispatchers
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class RecipesViewModel @Inject constructor(
    private val getRecipesUseCase: GetRecipesUseCase,
    coroutinesDispatchers: CoroutinesDispatchers
) : ViewModel() {

    var currentSearchQuery = ""


    private val action = MutableLiveData<Action>()
    val state: LiveData<UiState> = action
        .switchMap { action ->
            when (action) {
                is Action.Search, Action.Sort -> {
                    getRecipesUseCase(
                        GetRecipesUseCase.GetRecipesParams(currentSearchQuery, getPageConfig())
                    ).cachedIn(viewModelScope).map {
                        UiState.SearchResult(it)
                    }.asLiveData(coroutinesDispatchers.main())
                }
            }
        }

    fun recipesPagingData(query: String): Flow<PagingData<Recipe>> {
        return getRecipesUseCase(
            GetRecipesUseCase.GetRecipesParams(query, getPageConfig())
        ).cachedIn(viewModelScope)
    }

    private fun getPageConfig() = PagingConfig(
        pageSize = 2
    )

    fun searchRecipes() {
        action.value = Action.Search
    }

    fun applySort() {
        action.value = Action.Sort
    }

    fun closeSearch() {
        if(currentSearchQuery.isNotEmpty()) {
            currentSearchQuery = ""
        }
    }

    sealed class UiState {
        data class SearchResult(val data: PagingData<Recipe>): UiState()
    }

    sealed class Action {
        object Search: Action()
        object Sort: Action()
    }
}