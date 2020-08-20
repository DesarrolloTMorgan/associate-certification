package com.omnicluster.associatecertification.androidcore

import com.google.android.material.snackbar.Snackbar
import com.omnicluster.associatecertification.R
import com.omnicluster.associatecertification.databinding.FragmentSnackbarsBinding
import com.omnicluster.associatecertification.util.BaseFragment
import com.omnicluster.associatecertification.util.extensions.viewLifecycle

class SnackBarsFragment : BaseFragment(R.layout.fragment_snackbars) {

    private val binding by viewLifecycle(FragmentSnackbarsBinding::bind)

    override fun setupView() {
        binding.btnShowSimpleSnackbar.setOnClickListener {
            Snackbar.make(requireView(), R.string.simple_snackbar_message, Snackbar.LENGTH_SHORT).show()
        }

    }
}