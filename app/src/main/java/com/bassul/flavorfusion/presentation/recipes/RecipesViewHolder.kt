package com.bassul.flavorfusion.presentation.recipes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bassul.core.domain.model.Recipe
import com.bassul.flavorfusion.databinding.ItemRecipeBinding
import com.bassul.flavorfusion.util.OnRecipesItemClick
import com.bumptech.glide.Glide

class RecipesViewHolder(
    itemRecipesBinding: ItemRecipeBinding,
    private val onItemClick: OnRecipesItemClick

) : ViewHolder(itemRecipesBinding.root) {

    private val textTitle = itemRecipesBinding.textTitle
    private val imageRecipe = itemRecipesBinding.imageRecipe

    fun bind(recipe: Recipe) {
        textTitle.text = recipe.title
        imageRecipe.transitionName = recipe.title

        Glide.with(itemView)
            .load(recipe.image)
            .fallback(androidx.appcompat.R.drawable.btn_checkbox_checked_mtrl)
            .into(imageRecipe)

        itemView.setOnClickListener {
            onItemClick.invoke(recipe, imageRecipe)
        }
    }

    companion object {
        fun create(
            parent: ViewGroup,
            onItemClick: OnRecipesItemClick
        ): RecipesViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val itemBinding = ItemRecipeBinding.inflate(inflater, parent, false)
            return RecipesViewHolder(itemBinding, onItemClick)
        }
    }
}