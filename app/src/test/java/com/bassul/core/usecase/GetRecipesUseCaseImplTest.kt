package com.bassul.core.usecase

import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.bassul.core.data.repository.RecipesRepository
import com.bassul.core.data.repository.StorageRepository
import com.bassul.testing.MainCoroutineRule
import com.bassul.testing.model.RecipeFactory
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
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
    lateinit var recipesRepository: RecipesRepository

    @Mock
    lateinit var storageRepository: StorageRepository

    private lateinit var getRecipesUseCase: GetRecipesUseCase

    private val recipe = RecipeFactory().create(RecipeFactory.Recipe.Pasta)

    private val fakePagingData = PagingData.from(listOf(recipe))

    @Before
    fun setUp() {
        getRecipesUseCase = GetRecipesUseCaseImpl(recipesRepository, storageRepository)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `should validate flow paging data creation when invoke from use case is called`() =
        runTest{

            val pagingConfig = PagingConfig(2)
            val order = "asc"
            val orderBy = "calories"
            val query = "pasta"

            whenever(recipesRepository.getCachedRecipes(query, order, orderBy, pagingConfig))
                .thenReturn(flowOf(fakePagingData))

            whenever(storageRepository.sorting).thenReturn(flowOf(order))
            whenever(storageRepository.sortingBy).thenReturn(flowOf(orderBy))

            val result = getRecipesUseCase.invoke(GetRecipesUseCase.GetRecipesParams(query, pagingConfig))

            verify(recipesRepository).getCachedRecipes(query, order, orderBy, pagingConfig)

            assertNotNull(result.first())
    }
}
