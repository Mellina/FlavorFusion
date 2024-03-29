package com.bassul.flavorfusion.framework.di

import com.bassul.core.usecase.AddFavoriteUseCase
import com.bassul.core.usecase.AddFavoriteUseCaseImpl
import com.bassul.core.usecase.CheckFavoriteUseCase
import com.bassul.core.usecase.CheckFavoriteUseCaseImpl
import com.bassul.core.usecase.GetDetailsRecipeUseCase
import com.bassul.core.usecase.GetDetailsRecipeUseCaseImpl
import com.bassul.core.usecase.GetFavoritesUseCase
import com.bassul.core.usecase.GetFavoritesUseCaseImpl
import com.bassul.core.usecase.GetRecipesSortingUseCase
import com.bassul.core.usecase.GetRecipesSortingUseCaseImpl
import com.bassul.core.usecase.GetRecipesUseCase
import com.bassul.core.usecase.GetRecipesUseCaseImpl
import com.bassul.core.usecase.RemoveFavoriteUseCase
import com.bassul.core.usecase.RemoveFavoriteUseCaseImpl
import com.bassul.core.usecase.SaveRecipesSortingUseCase
import com.bassul.core.usecase.SaveRecipesSortingUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface UseCaseModule {

    @Binds
    fun bindGetRecipeUseCase(useCase: GetRecipesUseCaseImpl): GetRecipesUseCase

    @Binds
    fun bindGetDetailsRecipeUseCase(useCase: GetDetailsRecipeUseCaseImpl): GetDetailsRecipeUseCase

    @Binds
    fun bindCheckFavoriteUseCase(useCase: CheckFavoriteUseCaseImpl): CheckFavoriteUseCase

    @Binds
    fun bindAddFavoriteUseCase(useCase: AddFavoriteUseCaseImpl): AddFavoriteUseCase

    @Binds
    fun bindRemoveFavoriteUseCase(useCase: RemoveFavoriteUseCaseImpl): RemoveFavoriteUseCase

    @Binds
    fun bindGetFavoriteUseCase(useCase: GetFavoritesUseCaseImpl): GetFavoritesUseCase

    @Binds
    fun bindGetRecipesSortingUseCase(useCase: GetRecipesSortingUseCaseImpl): GetRecipesSortingUseCase

    @Binds
    fun bindSaveRecipesSortingUseCase(useCase: SaveRecipesSortingUseCaseImpl): SaveRecipesSortingUseCase
}
