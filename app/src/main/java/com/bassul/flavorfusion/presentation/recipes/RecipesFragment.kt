package com.bassul.flavorfusion.presentation.recipes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import com.bassul.flavorfusion.databinding.FragmentRecipesBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RecipesFragment : Fragment() {

    private var _binding: FragmentRecipesBinding? = null
    private val binding: FragmentRecipesBinding get() = _binding!!

    private lateinit var recipesAdapter: RecipesAdapter

    private val viewModel: RecipesViewModel by viewModels()

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

        lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.recipesPagingData("").collect { pagingData ->
                    recipesAdapter.submitData(pagingData)
                }
            }
        }
    }

    private fun initRecipesAdapter() {
        recipesAdapter = RecipesAdapter()
        with(binding.recyclerRecipes) {
            setHasFixedSize(true)
            adapter = recipesAdapter
        }
    }

    private fun observeInitialLoadState() {
        lifecycleScope.launch {
            recipesAdapter.loadStateFlow.collectLatest { loadState ->

                binding.flipperRecipes.displayedChild = when (loadState.refresh) {
                    is LoadState.Loading -> {
                        setShimmerVisibility(true)
                        FLIPPER_CHILD_LOADING
                    }

                    is LoadState.NotLoading -> {
                        setShimmerVisibility(false)
                        FLIPPER_CHILD_CHARACTERS
                    }
                    is LoadState.Error -> {
                        setShimmerVisibility(false)
                        binding.includeViewRecipesErrorState.buttonRetry.setOnClickListener {
                            recipesAdapter.refresh()
                        }
                        FLIPPER_CHILD_ERROR
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

    companion object {
        private const val FLIPPER_CHILD_LOADING = 0
        private const val FLIPPER_CHILD_CHARACTERS = 1
        private const val FLIPPER_CHILD_ERROR = 2
    }

}