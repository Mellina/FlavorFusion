package com.bassul.flavorfusion.presentation.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.bassul.core.usecase.GetDetailsRecipeUseCase
import com.bassul.core.usecase.base.ResultStatus
import com.bassul.flavorfusion.presentation.detail.DetailViewModel.UiState
import com.bassul.testing.MainCoroutineRule
import com.bassul.testing.model.DetailRecipeFactory
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.isA
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class DetailViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Mock
    lateinit var getDetailsRecipeUseCase: GetDetailsRecipeUseCase

    @Mock
    private lateinit var uiStateObserver: Observer<UiState>

    private val detailRecipe = listOf(DetailRecipeFactory().create(DetailRecipeFactory.FakeDetailRecipe.FakeDetailRecipe1))

    private lateinit var detailViewModel: DetailViewModel

    @Before
    fun setUp() {
        detailViewModel = DetailViewModel(getDetailsRecipeUseCase)
        detailViewModel.uiState.observeForever(uiStateObserver)
    }

    @Test
    fun `should notify uiState with Success from uiState when get details recipe returns success`() =
        runTest {

            whenever(getDetailsRecipeUseCase.invoke(any()))
                .thenReturn(
                    flowOf(
                        ResultStatus.Success(
                            detailRecipe[0]
                        )
                    )
                )
            detailViewModel.getDetailsRecipe(10)

            verify(uiStateObserver).onChanged(isA<UiState.Success>())
        }

    @Test
    fun `should notify uiState with Error from uiState when get details recipe returns a exception`() =
        runTest {
            whenever(getDetailsRecipeUseCase.invoke(any()))
                .thenReturn(
                    flowOf(
                        ResultStatus.Error(
                            Throwable()
                        )
                    )
                )
            detailViewModel.getDetailsRecipe(10)

            verify(uiStateObserver).onChanged(isA<UiState.Error>())
        }


}