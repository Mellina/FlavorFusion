package com.bassul.core.usecase

import com.bassul.core.data.repository.RecipesRepository
import com.bassul.core.usecase.base.ResultStatus
import com.bassul.testing.MainCoroutineRule
import com.bassul.testing.model.DetailRecipeFactory
import com.bassul.testing.model.RecipeFactory
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetDetailsRecipeUseCaseImplTest {

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var getDetailsRecipeUseCase: GetDetailsRecipeUseCase

    @Mock
    private lateinit var repository: RecipesRepository

    private val recipe = RecipeFactory().create(RecipeFactory.Recipe.Juice)
    private val detailsRecipe =
        DetailRecipeFactory().create(DetailRecipeFactory.FakeDetailRecipe.FakeCheeseMacaroni)

    @Before
    fun setUp() {
        getDetailsRecipeUseCase = GetDetailsRecipeUseCaseImpl(repository)
    }

    @Test
    fun `should return Success from ResultStatus when get request return success`() = runTest {
        //Arrange
        whenever(repository.getDetailsRecipe(recipe.id)).thenReturn(detailsRecipe)

        //Act
        val result = getDetailsRecipeUseCase.invoke(GetDetailsRecipeUseCase.GetDetailsRecipeParams(recipe.id))

        //Assert
        val resultList = result.toList()

        assertEquals(ResultStatus.Loading, resultList[0])
        assertTrue(resultList[1] is ResultStatus.Success)
    }

    @Test
    fun `should return Error from ResultStatus when get request return error`() = runTest {
        //Arrange
        whenever(repository.getDetailsRecipe(recipe.id)).thenAnswer{ throw Throwable() }

        //Act
        val result = getDetailsRecipeUseCase.invoke(GetDetailsRecipeUseCase.GetDetailsRecipeParams(recipe.id))

        //Assert
        val resultList = result.toList()

        assertEquals(ResultStatus.Loading, resultList[0])
        assertTrue(resultList[1] is ResultStatus.Error)
    }

}