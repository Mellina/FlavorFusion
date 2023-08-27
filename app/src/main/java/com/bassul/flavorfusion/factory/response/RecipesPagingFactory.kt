package com.bassul.flavorfusion.factory.response

import com.bassul.core.domain.model.Recipe
import com.bassul.core.domain.model.RecipePaging
import com.bassul.flavorfusion.framework.network.response.DataWrapperResponse
import com.bassul.flavorfusion.framework.network.response.RecipeResponse

class RecipesPagingFactory {

    fun create() = RecipePaging(
        offset = 0,
        totalResults = 20,
        listOf(
            Recipe(
                0,
                "Pasta",
                ""
            ),
            Recipe(
                1,
                "Juice",
                ""
            )
        ),
    )
}