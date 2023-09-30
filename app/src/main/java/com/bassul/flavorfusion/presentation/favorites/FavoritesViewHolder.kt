package com.bassul.flavorfusion.presentation.favorites

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bassul.flavorfusion.databinding.ItemRecipeBinding
import com.bassul.flavorfusion.framework.imageloader.ImageLoader
import com.bassul.flavorfusion.presentation.common.GenericViewHolder

class FavoritesViewHolder(
    itemBinding: ItemRecipeBinding,
    private val imageLoader: ImageLoader
) : GenericViewHolder<FavoriteItem>(itemBinding) {

    private val textName: TextView = itemBinding.textTitle
    private val imageRecipe: ImageView = itemBinding.imageRecipe

    override fun bind(data: FavoriteItem) {
        textName.text = data.name
        imageLoader.load(
            imageRecipe,
            data.imageUrl,
        )
    }

    companion object {
        fun create(parent: ViewGroup, imageLoader: ImageLoader): FavoritesViewHolder {
            val itemBinding = ItemRecipeBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)

            return FavoritesViewHolder(itemBinding, imageLoader)
        }
    }
}