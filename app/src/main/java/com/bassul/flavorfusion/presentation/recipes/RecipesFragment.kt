package com.bassul.flavorfusion.presentation.recipes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.bassul.flavorfusion.databinding.FragmentRecipesBinding
import com.bassul.flavorfusion.framework.imageloader.ImageLoader
import com.bassul.flavorfusion.presentation.detail.DetailViewArg
import com.bassul.flavorfusion.presentation.recipes.adapters.RecipesAdapter
import com.bassul.flavorfusion.presentation.recipes.adapters.RecipesLoadMoreStateAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class RecipesFragment : Fragment() {

    private var _binding: FragmentRecipesBinding? = null
    private val binding: FragmentRecipesBinding get() = _binding!!

    private val recipesAdapter: RecipesAdapter by lazy {
        RecipesAdapter(imageLoader) { recipe, view ->
            val extras = FragmentNavigatorExtras(
                view to recipe.title
            )

            val directions = RecipesFragmentDirections
                .actionRecipesFragmentToDetailFragment(
                    recipe.title,
                    DetailViewArg(recipe.id, recipe.title, recipe.image)
                )

            findNavController().navigate(directions, extras)
        }
    }

    private val viewModel: RecipesViewModel by viewModels()

    @Inject
    lateinit var imageLoader: ImageLoader

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentRecipesBinding.inflate(
        inflater,
        container,
        false
    ).apply {
        _binding = this
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecipesAdapter()
        observeInitialLoadState()

        viewModel.state.observe(viewLifecycleOwner) { uiState ->
            when (uiState) {
                is RecipesViewModel.UiState.SearchResult -> {
                    recipesAdapter.submitData(
                        viewLifecycleOwner.lifecycle,
                        uiState.data
                    )
                }
            }

        }

        viewModel.searchRecipes()
    }

    private fun initRecipesAdapter() {
        postponeEnterTransition()
        with(binding.recyclerRecipes) {
            setHasFixedSize(true)
            adapter = recipesAdapter.withLoadStateFooter(
                footer = RecipesLoadMoreStateAdapter(
                    retry = recipesAdapter::retry
                )
            )
            viewTreeObserver.addOnPreDrawListener {
                startPostponedEnterTransition()
                true
            }
        }
    }

    private fun observeInitialLoadState() {
        lifecycleScope.launch {
            recipesAdapter.loadStateFlow.collectLatest { loadState ->

                binding.flipperRecipes.displayedChild = when {

                    loadState.mediator?.refresh is LoadState.Loading -> {
                        setShimmerVisibility(true)
                        FLIPPER_CHILD_LOADING
                    }

                    loadState.mediator?.refresh is LoadState.Error
                            && recipesAdapter.itemCount == 0
                    -> {
                        setShimmerVisibility(false)
                        binding.includeViewRecipesErrorState.buttonRetry.setOnClickListener {
                            recipesAdapter.retry()
                        }
                        FLIPPER_CHILD_ERROR
                    }

                    loadState.source.refresh is LoadState.NotLoading
                            || loadState.mediator?.refresh is LoadState.NotLoading -> {
                        setShimmerVisibility(false)
                        FLIPPER_CHILD_CHARACTERS
                            }

                    else -> {
                        setShimmerVisibility(false)
                        FLIPPER_CHILD_CHARACTERS
                    }

                }
            }
        }
    }

    private fun setShimmerVisibility(visibility: Boolean) {
        binding.includeViewRecipesLoadingState.shimmerRecipes.run {
            isVisible = visibility
            if (visibility) {
                startShimmer()
            } else {
                stopShimmer()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val FLIPPER_CHILD_LOADING = 0
        private const val FLIPPER_CHILD_CHARACTERS = 1
        private const val FLIPPER_CHILD_ERROR = 2
    }

}