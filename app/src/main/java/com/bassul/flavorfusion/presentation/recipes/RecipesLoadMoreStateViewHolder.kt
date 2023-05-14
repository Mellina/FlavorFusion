package com.bassul.flavorfusion.presentation.recipes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.bassul.flavorfusion.databinding.ItemRecipeLoadMoreStateBinding

class RecipesLoadMoreStateViewHolder(
    itemBinding: ItemRecipeLoadMoreStateBinding,
    retry: () -> Unit
) : RecyclerView.ViewHolder(itemBinding.root) {

    private val binding = ItemRecipeLoadMoreStateBinding.bind(itemView)
    private val progressBarLoadingMore = binding.progressLoadingMore
    private val textTryAgainMessage = binding.textTryAgain.also {
        it.setOnClickListener { retry() }
    }

    fun bind(loadState: LoadState) {
        progressBarLoadingMore.isVisible = loadState is LoadState.Loading
        textTryAgainMessage.isVisible = loadState is LoadState.Error
    }

    companion object {
        fun create(parent: ViewGroup, retry: () -> Unit): RecipesLoadMoreStateViewHolder {
            val itemBinding = ItemRecipeLoadMoreStateBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)

            return RecipesLoadMoreStateViewHolder(itemBinding, retry)
        }
    }
}