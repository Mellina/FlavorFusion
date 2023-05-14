package com.bassul.flavorfusion.presentation.recipes

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter

class RecipesLoadMoreStateAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<RecipesLoadMoreStateViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ) = RecipesLoadMoreStateViewHolder.create(parent, retry)

    override fun onBindViewHolder(holder: RecipesLoadMoreStateViewHolder, loadState: LoadState) =
        holder.bind(loadState)

}