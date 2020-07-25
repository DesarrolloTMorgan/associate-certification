package com.omnicluster.associatecertification.androidcore

import android.view.Gravity
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import com.google.android.material.textview.MaterialTextView
import com.omnicluster.associatecertification.R
import com.omnicluster.associatecertification.databinding.FragmentToastsBinding
import com.omnicluster.associatecertification.util.BaseFragment
import com.omnicluster.associatecertification.util.extensions.viewLifecycle


class ToastsFragment : BaseFragment(R.layout.fragment_toasts) {

    private val binding by viewLifecycle(FragmentToastsBinding::bind)

    override fun setupView() {
        binding.btnShowSimpleToast.setOnClickListener {
            Toast.makeText(requireContext(), getString(R.string.single_toast_message), Toast.LENGTH_SHORT).show()
        }


        binding.btnShowCustomLayoutToast.setOnClickListener {
            makeCustomLayoutToast(
                message = getString(R.string.single_toast_message),
                toastDuration = Toast.LENGTH_LONG,
                customLayout = R.layout.custom_toast_layout
            )
        }

    }

    private fun makeCustomLayoutToast(
        message: String,
        toastDuration: Int,
        xOffset: Int = 0,
        yOffset: Int = 0,
        @LayoutRes customLayout: Int? = null
    ) {
        with(Toast.makeText(requireContext(), message, toastDuration)) {
            setGravity(Gravity.CENTER_VERTICAL, xOffset, yOffset)
            customLayout?.let {
                val container: ViewGroup? =
                    requireActivity().findViewById(R.id.custom_toast_container)
                val layout: ViewGroup? =
                    layoutInflater.inflate(it, container) as? ViewGroup
                val text: MaterialTextView? = layout?.findViewById(R.id.tv_toast)
                text?.text = message
                this.setText("")
                this.view = layout
            }
            show()
        }
    }
}