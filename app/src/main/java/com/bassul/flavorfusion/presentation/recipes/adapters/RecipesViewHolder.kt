package com.bassul.flavorfusion.presentation.recipes.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bassul.core.domain.model.Recipe
import com.bassul.flavorfusion.databinding.ItemRecipeBinding
import com.bassul.flavorfusion.framework.imageloader.ImageLoader
import com.bassul.flavorfusion.util.OnRecipesItemClick
import javax.inject.Inject

class RecipesViewHolder @Inject constructor(
    private val imageLoader: ImageLoader,
    itemRecipesBinding: ItemRecipeBinding,
    private val onItemClick: OnRecipesItemClick

) : ViewHolder(itemRecipesBinding.root) {

    private val textTitle = itemRecipesBinding.textTitle
    private val imageRecipe = itemRecipesBinding.imageRecipe

    fun bind(recipe: Recipe) {
        textTitle.text = recipe.title
        imageRecipe.transitionName = recipe.title
        imageLoader.load(imageRecipe, recipe.image)

        itemView.setOnClickListener {
            onItemClick.invoke(recipe, imageRecipe)
        }
    }

    companion object {
        fun create(
            imageLoader: ImageLoader,
            parent: ViewGroup,
            onItemClick: OnRecipesItemClick
        ): RecipesViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val itemBinding = ItemRecipeBinding.inflate(inflater, parent, false)
            return RecipesViewHolder(imageLoader, itemBinding, onItemClick)
        }
    }
}