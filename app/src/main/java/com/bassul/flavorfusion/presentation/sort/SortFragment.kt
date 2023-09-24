package com.bassul.flavorfusion.presentation.sort

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.forEach
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bassul.core.data.repository.StorageConstants.ORDER_ASCENDING
import com.bassul.core.data.repository.StorageConstants.ORDER_BY_CALORIES
import com.bassul.core.data.repository.StorageConstants.ORDER_BY_POPULARITY
import com.bassul.core.data.repository.StorageConstants.ORDER_BY_TIME
import com.bassul.core.data.repository.StorageConstants.ORDER_DESCENDING
import com.bassul.flavorfusion.R
import com.bassul.flavorfusion.databinding.FragmentSortBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SortFragment : BottomSheetDialogFragment() {

    private val viewModel: SortViewModel by viewModels()

    private var _binding: FragmentSortBinding? = null
    private val binding: FragmentSortBinding get() = _binding!!

    private var order = ORDER_ASCENDING
    private var orderBy = ORDER_BY_POPULARITY

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentSortBinding.inflate(
        inflater,
        container,
        false
    ).apply {
        _binding = this
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setChipGroupListeners()
        observeUiState()
    }

    private fun setChipGroupListeners() {
        binding.chipGroupOrder.setOnCheckedChangeListener { group, checkedId ->
            val chip = group.findViewById<Chip>(checkedId)
            order = getOrderValue(chip.id)
        }

        binding.chipGroupOrderBy.setOnCheckedChangeListener { group, checkedId ->
            val chip = group.findViewById<Chip>(checkedId)
            orderBy = getOrderByValue(chip.id)
        }

        binding.buttonApplySort.setOnClickListener {
            viewModel.applySorting(orderBy, order)
           // dismiss()
        }
    }

    private fun observeUiState() {
        viewModel.state.observe(viewLifecycleOwner) { uiState ->
            when (uiState) {
                is SortViewModel.UiState.SortingResult -> {
                    val order = uiState.storedSorting.first
                    val orderBy = uiState.storedSorting.second

                    binding.chipGroupOrder.forEach {
                        val chip = it as Chip

                        if (getOrderValue(chip.id) == order) {
                            chip.isChecked = true
                        }
                    }

                    binding.chipGroupOrderBy.forEach {
                        val chip = it as Chip

                        if (getOrderByValue(chip.id) == orderBy) {
                            chip.isChecked = true
                        }
                    }
                }

                is SortViewModel.UiState.ApplyState.Loading -> {
                    binding.flipperApply.displayedChild = FLIPPER_CHILD_PROGRESS
                }

                is SortViewModel.UiState.ApplyState.Success -> {


                    findNavController().run {
                        previousBackStackEntry?.savedStateHandle?.set(
                            SORTING_APPLIED_BASK_STACK_KEY,
                            true
                        )

                        popBackStack()
                    }
                    binding.flipperApply.displayedChild = FLIPPER_CHILD_BUTTON
                }

                is SortViewModel.UiState.ApplyState.Error -> {
                    binding.flipperApply.displayedChild = FLIPPER_CHILD_BUTTON
                }
            }

        }
    }

    private fun getOrderValue(chipId: Int): String = when (chipId) {
        R.id.chip_ascending -> ORDER_ASCENDING
        R.id.chip_descending -> ORDER_DESCENDING
        else -> ORDER_ASCENDING
    }

    private fun getOrderByValue(chipId: Int): String = when (chipId) {
        R.id.chip_popularity -> ORDER_BY_POPULARITY
        R.id.chip_calories -> ORDER_BY_CALORIES
        R.id.chip_time -> ORDER_BY_TIME
        else -> ORDER_ASCENDING
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val FLIPPER_CHILD_BUTTON = 0
        private const val FLIPPER_CHILD_PROGRESS = 1
        const val SORTING_APPLIED_BASK_STACK_KEY = "sortingAppliedBaskStackKey"
    }
}