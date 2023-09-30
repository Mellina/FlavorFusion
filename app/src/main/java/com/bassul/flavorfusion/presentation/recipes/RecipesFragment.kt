package com.bassul.flavorfusion.presentation.recipes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.bassul.flavorfusion.R
import com.bassul.flavorfusion.databinding.FragmentRecipesBinding
import com.bassul.flavorfusion.framework.imageloader.ImageLoader
import com.bassul.flavorfusion.presentation.detail.DetailViewArg
import com.bassul.flavorfusion.presentation.recipes.adapters.RecipesAdapter
import com.bassul.flavorfusion.presentation.recipes.adapters.RecipesLoadMoreStateAdapter
import com.bassul.flavorfusion.presentation.recipes.adapters.RecipesRefreshStateAdapter
import com.bassul.flavorfusion.presentation.sort.SortFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class RecipesFragment : Fragment(), SearchView.OnQueryTextListener,
    MenuItem.OnActionExpandListener {

    private var _binding: FragmentRecipesBinding? = null
    private val binding: FragmentRecipesBinding get() = _binding!!

    private val viewModel: RecipesViewModel by viewModels()

    private lateinit var searchView: SearchView

    @Inject
    lateinit var imageLoader: ImageLoader

    private val headerAdapter: RecipesRefreshStateAdapter by lazy {
        RecipesRefreshStateAdapter(
            recipesAdapter::retry
        )
    }

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

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
        observeSortingData()

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
            adapter = recipesAdapter.withLoadStateHeaderAndFooter(
                header = headerAdapter,
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
                headerAdapter.loadState = loadState.mediator
                    ?.refresh
                    ?.takeIf {
                        it is LoadState.Error && recipesAdapter.itemCount > 0
                    } ?: loadState.prepend

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

    private fun observeSortingData() {
        val navBackStackEntry = findNavController().getBackStackEntry(R.id.recipesFragment)
        val observer = LifecycleEventObserver { _, event ->
            val isSortingApplied = navBackStackEntry.savedStateHandle.contains(
                SortFragment.SORTING_APPLIED_BASK_STACK_KEY
            )

            if (event == Lifecycle.Event.ON_RESUME && isSortingApplied) {
                viewModel.applySort()
                navBackStackEntry.savedStateHandle.remove<Boolean>(
                    SortFragment.SORTING_APPLIED_BASK_STACK_KEY
                )
            }
        }

        navBackStackEntry.lifecycle.addObserver(observer)

        viewLifecycleOwner.lifecycle.addObserver(LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_DESTROY) {
                navBackStackEntry.lifecycle.removeObserver(observer)
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.recipes_menu_items, menu)

        val searchItem = menu.findItem(R.id.menu_search)
        searchView = searchItem.actionView as SearchView
        searchItem.setOnActionExpandListener(this)

        if (viewModel.currentSearchQuery.isNotEmpty()) {
            searchItem.expandActionView()
            searchView.setQuery(viewModel.currentSearchQuery, false)
        }

        searchView.run {
            isSubmitButtonEnabled = true
            setOnQueryTextListener(this@RecipesFragment)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_sort -> {
                findNavController().navigate(R.id.action_recipesFragment_to_sortFragment)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return query?.let {
            viewModel.currentSearchQuery = it
            viewModel.searchRecipes()
            true
        } ?: false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }

    override fun onMenuItemActionExpand(item: MenuItem): Boolean {
        return true
    }

    override fun onMenuItemActionCollapse(item: MenuItem): Boolean {
        viewModel.closeSearch()
        viewModel.searchRecipes()
        return true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        searchView.setOnQueryTextListener(null)
        _binding = null
    }

    companion object {
        private const val FLIPPER_CHILD_LOADING = 0
        private const val FLIPPER_CHILD_CHARACTERS = 1
        private const val FLIPPER_CHILD_ERROR = 2
    }
}