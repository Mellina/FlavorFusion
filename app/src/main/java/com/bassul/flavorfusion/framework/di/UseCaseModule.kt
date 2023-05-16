package com.bassul.flavorfusion.framework.di

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
    fun bindRecipeUseCase(useCase: GetRecipesUseCaseImpl): GetRecipesUseCase
}