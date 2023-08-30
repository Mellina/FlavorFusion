package com.bassul.flavorfusion.framework.di

import com.bassul.core.usecase.AddFavoriteUseCase
import com.bassul.core.usecase.AddFavoriteUseCaseImpl
import com.bassul.core.usecase.GetDetailsRecipeUseCase
import com.bassul.core.usecase.GetDetailsRecipeUseCaseImpl
import com.bassul.core.usecase.GetRecipesUseCase
import com.bassul.core.usecase.GetRecipesUseCaseImpl
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
    fun bindAddFavoriteUseCase(useCase: AddFavoriteUseCaseImpl): AddFavoriteUseCase
}