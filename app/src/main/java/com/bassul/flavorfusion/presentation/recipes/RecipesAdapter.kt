package com.bassul.flavorfusion.presentation.recipes

import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.bassul.core.domain.model.Recipe
import com.bassul.flavorfusion.framework.imageloader.ImageLoader
import com.bassul.flavorfusion.util.OnRecipesItemClick


class RecipesAdapter(
    private val imageLoader: ImageLoader,
    private val onItemClick: OnRecipesItemClick
) : PagingDataAdapter<Recipe, RecipesViewHolder>(diffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipesViewHolder {
        return RecipesViewHolder.create(imageLoader, parent, onItemClick)
    }

    override fun onBindViewHolder(holder: RecipesViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<Recipe>() {
            override fun areItemsTheSame(
                oldItem: Recipe,
                newItem: Recipe
            ): Boolean {
                return oldItem.title == newItem.title
            }

            override fun areContentsTheSame(
                oldItem: Recipe,
                newItem: Recipe
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}