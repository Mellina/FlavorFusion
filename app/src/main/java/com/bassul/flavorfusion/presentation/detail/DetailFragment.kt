package com.bassul.flavorfusion.presentation.detail

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.bassul.flavorfusion.R
import com.bassul.flavorfusion.databinding.FragmentDetailBinding
import com.bassul.flavorfusion.framework.imageloader.ImageLoader
import com.bassul.flavorfusion.presentation.extensions.showShortToast
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
            )//TODO: provisório - colocar imagem de erro ao carregar
        }

        //TODO: Preencher dish types
        val textWithMarkers = StringBuilder()
        textWithMarkers.append("• This is the first line\n")
        textWithMarkers.append("• This is the second line\n")
        textWithMarkers.append("• This is the last line")
        binding.dishType.setText(textWithMarkers)
        setSharedElementTransitionOnEnter()

        loadDetailsAndObserveUiState(detailViewArg)
        setAndObserveFavoriteUiState(detailViewArg)
    }

    private fun loadDetailsAndObserveUiState(detailViewArg: DetailViewArg) {
        viewModel.details.load(detailViewArg.id)
        viewModel.details.state.observe(viewLifecycleOwner) { uiState ->
            binding.flipperDetails.displayedChild = when (uiState) {
                UiActionStateLiveData.UiState.Loading -> {
                    FLIPPER_CHILD_POSITION_LOADING
                }

                is UiActionStateLiveData.UiState.Success -> {
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

                is UiActionStateLiveData.UiState.Error -> {
                    binding.includeErrorView.buttonRetry.setOnClickListener {
                        viewModel.details.load(detailViewArg.id)
                    }
                    FLIPPER_CHILD_POSITION_ERROR
                }
            }

        }


    }

    private fun setAndObserveFavoriteUiState(detailViewArg: DetailViewArg) {
        viewModel.favorite.run {
            checkFavorite(detailViewArg.id)

            binding.imageFavoriteIcon.setOnClickListener {
                update(detailViewArg)
            }

            state.observe(viewLifecycleOwner) { uiState ->
                binding.flipperFavorite.displayedChild = when (uiState) {
                    FavoriteUiActionStateLiveData.UiState.Loading -> FLIPPER_FAVORITE_CHILD_POSITION_LOADING
                    is FavoriteUiActionStateLiveData.UiState.Icon -> {
                        binding.imageFavoriteIcon.setImageResource(uiState.icon)
                        FLIPPER_FAVORITE_CHILD_POSITION_IMAGE
                    }

                    is FavoriteUiActionStateLiveData.UiState.Error -> {
                        showShortToast(uiState.messageResId)
                        FLIPPER_FAVORITE_CHILD_POSITION_IMAGE
                    }
                }
            }
        }


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

        private const val FLIPPER_FAVORITE_CHILD_POSITION_IMAGE = 0
        private const val FLIPPER_FAVORITE_CHILD_POSITION_LOADING = 1
    }

}