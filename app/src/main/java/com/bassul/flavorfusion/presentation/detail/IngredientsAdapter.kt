package com.bassul.flavorfusion.presentation.detail

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.bassul.core.domain.model.ExtendedIngredient
import com.bassul.flavorfusion.framework.imageloader.ImageLoader

class IngredientsAdapter(
    private val imageLoader: ImageLoader,
) : ListAdapter<ExtendedIngredient, IngredientsViewHolder>(diffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientsViewHolder {
        return IngredientsViewHolder.create(imageLoader, parent)
    }

    override fun onBindViewHolder(holder: IngredientsViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<ExtendedIngredient>() {
            override fun areItemsTheSame(
                oldItem: ExtendedIngredient,
                newItem: ExtendedIngredient
            ): Boolean {
                return oldItem.name == newItem.name
            }

            override fun areContentsTheSame(
                oldItem: ExtendedIngredient,
                newItem: ExtendedIngredient
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}