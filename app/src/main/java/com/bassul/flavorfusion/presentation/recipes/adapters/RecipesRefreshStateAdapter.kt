package com.bassul.flavorfusion.presentation.recipes.adapters

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter

class RecipesRefreshStateAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<RecipesRefreshStateViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ) = RecipesRefreshStateViewHolder.create(parent, retry)

    override fun onBindViewHolder(holder: RecipesRefreshStateViewHolder, loadState: LoadState) =
        holder.bind(loadState)

}