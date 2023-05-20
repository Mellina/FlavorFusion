package com.bassul.core.usecase

import androidx.paging.PagingConfig
import com.bassul.core.data.repository.RecipesRepository
import com.bassul.testing.MainCoroutineRule
import com.bassul.testing.model.RecipeFactory
import com.bassul.testing.pagingsource.PagingSourceFactory
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetRecipesUseCaseImplTest {

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Mock
    lateinit var repository: RecipesRepository

    private lateinit var getRecipesUseCase: GetRecipesUseCase

    private val recipe = RecipeFactory().create(RecipeFactory.Recipe.Pasta)

    private val fakePagingSource = PagingSourceFactory().create(listOf(recipe))

    @Before
    fun setUp() {
        getRecipesUseCase = GetRecipesUseCaseImpl(repository)
    }

    @Test
    fun `should validate flow paging data creation when invoke from use case is called`() =
        runBlockingTest{

            whenever(repository.getRecipes(""))
                .thenReturn(fakePagingSource)

            val result = getRecipesUseCase.invoke(GetRecipesUseCase.GetRecipesParams("", PagingConfig(2)))

            verify(repository).getRecipes("")

            assertNotNull(result.first())
    }
}