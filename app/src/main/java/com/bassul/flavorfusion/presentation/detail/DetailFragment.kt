package com.bassul.flavorfusion.presentation.detail

import android.os.Bundle
import android.transition.TransitionInflater
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.bassul.flavorfusion.R
import com.bassul.flavorfusion.databinding.FragmentDetailBinding
import com.bassul.flavorfusion.framework.imageloader.ImageLoader
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding: FragmentDetailBinding get() = _binding!!

    private lateinit var ingredientsAdapter: IngredientsAdapter

    private val viewModel: DetailViewModel by viewModels()

    private val args by navArgs<DetailFragmentArgs>()

    @Inject
    lateinit var imageLoader: ImageLoader
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentDetailBinding.inflate(
        inflater, container, false
    ).apply {
        _binding = this
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val detailViewArg = args.detailViewArg
        binding.imageRecipe.run {
            transitionName = detailViewArg.name
            imageLoader.load(
                this,
                detailViewArg.imageUrl,
                R.drawable.ic_launcher_background
            )//provisório - colocar imagem de erro ao carregar
        }
        setSharedElementTransitionOnEnter()

        viewModel.uiState.observe(viewLifecycleOwner) { uiState ->
            binding.flipperDetails.displayedChild = when (uiState) {
                DetailViewModel.UiState.Loading -> {
                    FLIPPER_CHILD_POSITION_LOADING
                }

                is DetailViewModel.UiState.Success -> {
                    uiState.detailsRecipe.toString()

                    ingredientsAdapter = IngredientsAdapter(imageLoader)
                    lifecycleScope.launch {
                        viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                            ingredientsAdapter.submitList(uiState.detailsRecipe.extendedIngredients)
                        }
                    }
                    binding.ingredients.listIngredients.run {
                        setHasFixedSize(true)
                        adapter = ingredientsAdapter
                    }

                    FLIPPER_CHILD_POSITION_SUCCESS
                }

                is DetailViewModel.UiState.Error -> {
                    binding.includeErrorView.buttonRetry.setOnClickListener {
                        viewModel.getDetailsRecipe(detailViewArg.id)
                    }
                    FLIPPER_CHILD_POSITION_ERROR
                }
            }

        }

        viewModel.getDetailsRecipe(detailViewArg.id)
    }

    private fun setSharedElementTransitionOnEnter() {
        TransitionInflater.from(requireContext())
            .inflateTransition(android.R.transition.move).apply {
                sharedElementEnterTransition = this
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val FLIPPER_CHILD_POSITION_LOADING = 0
        private const val FLIPPER_CHILD_POSITION_SUCCESS = 1
        private const val FLIPPER_CHILD_POSITION_ERROR = 2
    }

}