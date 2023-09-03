package com.bassul.flavorfusion.presentation.recipes.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.bassul.flavorfusion.databinding.ItemRecipeLoadMoreStateBinding
import com.bassul.flavorfusion.databinding.ItemRecipeRefreshStateBinding

class RecipesRefreshStateViewHolder(
    itemBinding: ItemRecipeRefreshStateBinding,
    retry: () -> Unit
) : RecyclerView.ViewHolder(itemBinding.root) {

    private val binding = ItemRecipeRefreshStateBinding.bind(itemView)
    private val progressBarLoadingMore = binding.progressLoadingMore
    private val textTryAgainMessage = binding.textTryAgain.also {
        it.setOnClickListener { retry() }
    }

    fun bind(loadState: LoadState) {
        progressBarLoadingMore.isVisible = loadState is LoadState.Loading
        textTryAgainMessage.isVisible = loadState is LoadState.Error
    }

    companion object {
        fun create(parent: ViewGroup, retry: () -> Unit): RecipesRefreshStateViewHolder {
            val itemBinding = ItemRecipeRefreshStateBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)

            return RecipesRefreshStateViewHolder(itemBinding, retry)
        }
    }
}