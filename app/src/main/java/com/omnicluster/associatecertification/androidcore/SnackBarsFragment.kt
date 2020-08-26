package com.omnicluster.associatecertification.androidcore

import android.view.inputmethod.EditorInfo
import android.widget.RadioGroup
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import com.omnicluster.associatecertification.R
import com.omnicluster.associatecertification.databinding.FragmentSnackbarsBinding
import com.omnicluster.associatecertification.util.BaseFragment
import com.omnicluster.associatecertification.util.extensions.viewLifecycle

class SnackBarsFragment : BaseFragment(R.layout.fragment_snackbars) {

    private val binding by viewLifecycle(FragmentSnackbarsBinding::bind)
    private val viewModel by viewModels<AndroidCoreViewModel>()
    private var snackbarColor: Int = -1

    override fun setupView() {
        binding.btnShowSimpleSnackbar.setOnClickListener {
            Snackbar.make(requireView(), R.string.simple_snackbar_message, Snackbar.LENGTH_SHORT)
                .show()
        }
        binding.btnShowCustomSnackbar.setOnClickListener {
            showCustomSnackBar()
        }
        binding.colorSelectorLayout.rgColorSelector.setOnCheckedChangeListener(rgColorListener)
        binding.etCustomSnackbarMessage.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEND) {
                hideKeyBoard()
                binding.tilSnackbar.clearFocus()
                showCustomSnackBar()
            }
            return@setOnEditorActionListener true
        }

    }

    private val rgColorListener =
        RadioGroup.OnCheckedChangeListener { _, checkedId ->
            snackbarColor = when (checkedId) {
                R.id.rb_success_selector -> R.color.successColor
                R.id.rb_error_selector -> R.color.errorColor
                R.id.rb_warning_selector -> R.color.warningColor
                R.id.rb_information_selector -> R.color.infoColor
                else -> -1
            }
        }

    private fun showCustomSnackBar() {
        viewModel.buildCustomSnackBar(
            binding.etCustomSnackbarMessage.text.toString(),
            snackbarColor,
            requireView()
        ).show()
    }
}