package com.omnicluster.associatecertification.themes

import android.view.View
import android.widget.AdapterView
import com.omnicluster.associatecertification.R
import com.omnicluster.associatecertification.databinding.FragmentThemeChooserBinding
import com.omnicluster.associatecertification.util.BaseFragment
import com.omnicluster.associatecertification.util.Utils
import com.omnicluster.associatecertification.util.extensions.viewLifecycle

class ThemeChooserFragment : BaseFragment(R.layout.fragment_theme_chooser) {

    private val binding by viewLifecycle(FragmentThemeChooserBinding::bind)

    override fun setupView() {
        setUpSpinnerItemSelection()
    }

    private fun setUpSpinnerItemSelection() {
        val spinner = binding.spThemes
        spinner.setSelection(ThemeApplication.currentPosition)
        ThemeApplication.currentPosition = spinner.selectedItemPosition

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (ThemeApplication.currentPosition != position) {
                    Utils.changeToTheme(requireActivity(), position)
                }
                ThemeApplication.currentPosition = position
            }
        }
    }

}