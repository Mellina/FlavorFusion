package com.bassul.flavorfusion.presentation.recipes

import androidx.paging.PagingData
import com.bassul.core.domain.model.Recipe
import com.bassul.core.usecase.GetRecipesUseCase
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class RecipesViewModelTest {

    val testDispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()

    @Mock
    lateinit var getRecipesUseCase: GetRecipesUseCase

    private lateinit var recipesViewModel: RecipesViewModel

    private val pagingDataRecipe = PagingData.from(
        listOf(
            Recipe("Receita 1", ""),
            Recipe("Receita 2", "")
        )
    )

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
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