package com.bassul.flavorfusion.factory.response

import com.bassul.flavorfusion.framework.network.response.DataWrapperResponse
import com.bassul.flavorfusion.framework.network.response.RecipeResponse

class DataWrapperResponseFactory {

    fun create() = DataWrapperResponse(
        listOf(
            RecipeResponse(
                "Pasta",
                ""
            ),
            RecipeResponse(
                "Juice",
                ""
            )
        ),
        0,
        2
    )
}