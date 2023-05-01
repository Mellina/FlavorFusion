package com.bassul.flavorfusion.presentation.recipes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bassul.core.domain.model.Recipe
import com.bassul.flavorfusion.R
import com.bassul.flavorfusion.databinding.FragmentRecipesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecipesFragment : Fragment() {

    private var _binding: FragmentRecipesBinding? = null
    private val binding: FragmentRecipesBinding get() = _binding!!

    private val recipesAdapter = RecipesAdapter()

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

        @Suppress("MaxLineLength")
        recipesAdapter.submitList(
            listOf(
                Recipe("Sopa",
                    "http://i.annihil.us/u/prod/marvel/i/mg/b/40/image_not_available"),
                Recipe("Suco", "https://img.ibxk.com.br/materias/5866/21577.jpg"),
                Recipe("Feijão", "https://img.ibxk.com.br/materias/5866/21577.jpg"),
            )
        )
    }

    private fun initRecipesAdapter() {
        with(binding.recyclerRecipes){
            setHasFixedSize(true)
            adapter = recipesAdapter
        }
    }

}