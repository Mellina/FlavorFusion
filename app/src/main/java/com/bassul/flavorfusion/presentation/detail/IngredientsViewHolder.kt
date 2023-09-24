package com.bassul.flavorfusion.presentation.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bassul.core.domain.model.ExtendedIngredient
import com.bassul.flavorfusion.databinding.ItemIngredientBinding
import com.bassul.flavorfusion.framework.imageloader.ImageLoader
import javax.inject.Inject

class IngredientsViewHolder @Inject constructor(
    private val imageLoader: ImageLoader,
    itemIngredientBinding: ItemIngredientBinding

) : ViewHolder(itemIngredientBinding.root) {

    private val amount = itemIngredientBinding.amountItemIngredient
    private val image = itemIngredientBinding.imageItemIngredient
    private val name = itemIngredientBinding.nameItemIngredient

    fun bind(ingredient: ExtendedIngredient) {
        amount.text = ingredient.amount.toString()+" "+ingredient.unit
        image.transitionName = ingredient.image
        imageLoader.load(
            image,
            ingredient.getImageUrl(),
            androidx.appcompat.R.drawable.btn_checkbox_checked_mtrl
        )
        name.text = ingredient.name
    }

    companion object {
        fun create(
            imageLoader: ImageLoader,
            parent: ViewGroup,
        ): IngredientsViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val itemBinding = ItemIngredientBinding.inflate(inflater, parent, false)
            return IngredientsViewHolder(imageLoader, itemBinding)
        }
    }
}
