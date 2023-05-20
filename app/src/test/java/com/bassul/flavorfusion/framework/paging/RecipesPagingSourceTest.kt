package com.bassul.flavorfusion.framework.paging

import androidx.paging.PagingSource
import com.bassul.core.data.repository.RecipesRemoteDataSource
import com.bassul.flavorfusion.factory.response.DataWrapperResponseFactory
import com.bassul.flavorfusion.framework.network.response.DataWrapperResponse
import com.bassul.testing.MainCoroutineRule
import com.bassul.testing.model.RecipeFactory
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class RecipesPagingSourceTest {

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Mock
    lateinit var remoteDataSource: RecipesRemoteDataSource<DataWrapperResponse>

    private val dataWrapperResponseFactory = DataWrapperResponseFactory()

    private val recipeFactory = RecipeFactory()

    private lateinit var recipesPagingSource: RecipesPagingSource

    @Before
    fun setUp() {
        recipesPagingSource = RecipesPagingSource(remoteDataSource, "")
    }

    @Test
    fun `should return a success load result w3hen load is called`() = runBlockingTest {
        // Arrange
        whenever(remoteDataSource.fetchRecipes(any()))
            .thenReturn(dataWrapperResponseFactory.create())

        //Act
        val result = recipesPagingSource.load(
            PagingSource.LoadParams.Refresh(
                null,
                loadSize = 2,
                false
            )
        )

        //Assert
        val expected = listOf(
            recipeFactory.create(RecipeFactory.Recipe.Pasta),
            recipeFactory.create(RecipeFactory.Recipe.Juice)
        )

        assertEquals(
            PagingSource.LoadResult.Page(
                data = expected,
                null,
                2
            ),
            result
        )
    }
}