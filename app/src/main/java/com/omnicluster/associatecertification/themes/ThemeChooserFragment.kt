package com.omnicluster.associatecertification.themes

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import com.omnicluster.associatecertification.R
import com.omnicluster.associatecertification.databinding.FragmentThemeChooserBinding
import com.omnicluster.associatecertification.util.BaseFragment
import com.omnicluster.associatecertification.util.Utils
import com.omnicluster.associatecertification.util.extensions.viewLifecycle

class ThemeChooserFragment : BaseFragment(R.layout.fragment_theme_chooser) {

    private val binding by viewLifecycle(FragmentThemeChooserBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Utils.onActivityCreateSetTheme(requireActivity())
        }
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