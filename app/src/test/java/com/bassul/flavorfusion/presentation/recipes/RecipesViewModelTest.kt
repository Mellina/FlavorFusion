package com.bassul.flavorfusion.presentation.recipes

import androidx.paging.PagingData
import com.bassul.core.usecase.GetRecipesUseCase
import com.bassul.testing.MainCoroutineRule
import com.bassul.testing.model.RecipeFactory
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class RecipesViewModelTest {

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Mock
    lateinit var getRecipesUseCase: GetRecipesUseCase

    private lateinit var recipesViewModel: RecipesViewModel

    private val recipeFactory = RecipeFactory()

    private val pagingDataRecipe = PagingData.from(
        listOf(
            recipeFactory.create(RecipeFactory.Recipe.Pasta),
            recipeFactory.create(RecipeFactory.Recipe.Juice)
        )
    )

    @Before
    fun setUp() {
        recipesViewModel = RecipesViewModel(getRecipesUseCase)
    }

    @Test
    fun `should validate the paging data object values when calling recipesPagingData`() =
        runBlockingTest {
            whenever(
                getRecipesUseCase.invoke(any())
            ).thenReturn(
                flowOf(pagingDataRecipe)
            )

            val result = recipesViewModel.recipesPagingData("")

            assertEquals(1, result.count())
        }

    @Test(expected = java.lang.RuntimeException::class)
    fun `should throw an exception when the calling to the use case returns an exception`() =
        runBlockingTest {
            whenever(
                getRecipesUseCase.invoke(any())
            ).thenThrow(
                RuntimeException()
            )

            recipesViewModel.recipesPagingData("")
        }
}