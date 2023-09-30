package com.bassul.flavorfusion.presentation.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bassul.core.domain.model.ExtendedIngredient
import com.bassul.flavorfusion.databinding.ItemIngredientBinding
import com.bassul.flavorfusion.framework.imageloader.ImageLoader
import java.util.Locale
import javax.inject.Inject

class IngredientsViewHolder @Inject constructor(
    private val imageLoader: ImageLoader,
    itemIngredientBinding: ItemIngredientBinding

) : ViewHolder(itemIngredientBinding.root) {

    private val amount = itemIngredientBinding.amountItemIngredient
    private val image = itemIngredientBinding.imageItemIngredient
    private val name = itemIngredientBinding.nameItemIngredient

    fun bind(ingredient: ExtendedIngredient) {
        amount.text = String.format(Locale.getDefault(), AMOUNT_FORMAT, ingredient.amount, ingredient.unit)
        image.transitionName = ingredient.image
        imageLoader.load(
            image,
            ingredient.getImageUrl(),
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

        const val AMOUNT_FORMAT = "%.2f %s"
    }
}
