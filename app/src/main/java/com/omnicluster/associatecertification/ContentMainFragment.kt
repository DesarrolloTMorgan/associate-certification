package com.omnicluster.associatecertification

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.omnicluster.associatecertification.databinding.FragmentContentMainBinding
import com.omnicluster.associatecertification.util.BaseFragment
import com.omnicluster.associatecertification.util.extensions.viewLifecycle

class ContentMainFragment : BaseFragment(R.layout.fragment_content_main) {

    private val binding by viewLifecycle(FragmentContentMainBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnGoToThemes.setOnClickListener {
            findNavController().navigate(R.id.action_fragment_main_content_to_fragment_theme_chooser)
        }
    }

}